package com.onlinestore.backend.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.backend.DTO.Response.DefaultResponse;
import com.onlinestore.backend.DTO.Requests.UpdateRequest;
import com.onlinestore.backend.Models.UserImage.UserImage;
import com.onlinestore.backend.Repositories.UserRepositories;
import com.onlinestore.backend.user.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepositories userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private KafkaProducer<List<Integer>> kafkaProducer;

    @Mock
    private RequestGenerateService<List<Integer>> requestGenerateService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void likeImage_ValidRequest_ShouldReturnOkResponse() {
        // Arrange
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setImageId(1L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");

        MyUser currentUser = new MyUser();
        currentUser.setId(1);
        when(userRepository.findByLogin("user")).thenReturn(Optional.of(currentUser));

        // Act
        ResponseEntity<DefaultResponse> response = userService.likeImage(likeDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Пин успешно сохранён", response.getBody().getMessage());

        verify(userImageRepository).save(any(UserImage.class));
    }

    @Test
    void likeImage_NullImageId_ShouldReturnBadRequest() {
        // Arrange
        LikeDTO likeDTO = new LikeDTO(); // ImageId not set

        // Act
        ResponseEntity<DefaultResponse> response = userService.likeImage(likeDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Получены не все данные", response.getBody().getMessage());
    }

    @Test
    void getCurrentUser_ValidUser_ShouldReturnUser() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");

        MyUser user = new MyUser();
        user.setLogin("user");
        when(userRepository.findByLogin("user")).thenReturn(Optional.of(user));

        // Act
        MyUser currentUser = userService.getCurrentUser();

        // Assert
        assertNotNull(currentUser);
        assertEquals("user", currentUser.getLogin());
    }

    @Test
    void getCurrentUser_UserNotFound_ShouldThrowException() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("nonexistent");

        when(userRepository.findByLogin("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, userService::getCurrentUser);
    }

    @Test
    void updateUser_ValidRequest_ShouldReturnOkResponse() {
        // Arrange
        UpdateRequest updateRequest = new UpdateRequest();

        MyUser currentUser = new MyUser();
        currentUser.setId(1);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(userRepository.findByLogin("user")).thenReturn(Optional.of(currentUser));

        when(userRepository.existsById(1)).thenReturn(true);

        MyUser updatedUser = new MyUser();
        updatedUser.setId(1);
        updatedUser.setEmail("newemail@example.com");
        when(modelMapper.map(updateRequest, MyUser.class)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Act
        ResponseEntity<Map<String, Object>> response = userService.updateUser(updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Данные пользователя обновлены", response.getBody().get("message"));

        verify(userRepository).save(updatedUser);
    }

    @Test
    void updateUser_UserNotFound_ShouldReturnNotFoundResponse() {
        // Arrange
        UpdateRequest updateRequest = new UpdateRequest();

        MyUser currentUser = new MyUser();
        currentUser.setId(1);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(userRepository.findByLogin("user")).thenReturn(Optional.of(currentUser));

        when(userRepository.existsById(1)).thenReturn(false);

        // Act
        ResponseEntity<Map<String, Object>> response = userService.updateUser(updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Пользователя не найден", response.getBody().get("message"));
    }
}
