package com.onlinestore.backend.Services;

import com.onlinestore.backend.util.PatternCustom;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.onlinestore.backend.DTO.Response.JwtAuthenticationResponse;
import com.onlinestore.backend.DTO.Requests.RegisterRequest;
import com.onlinestore.backend.Exceptions.JwtGenerateTokenExceptions;
import com.onlinestore.backend.user.MyUser;
import com.onlinestore.backend.user.MyUserDetails;
import com.onlinestore.backend.user.Role;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final PatternCustom patternCustom;

    public ResponseEntity<JwtAuthenticationResponse> registration(RegisterRequest request) {
        StringBuilder jwt = new StringBuilder();
        StringBuilder error =  new StringBuilder();

        if (request.getLogin() == null || request.getPassword() == null) {
            error.append("Нет логина или пароля");
            log.error(error.toString());
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(error.toString())
                            .code(HttpStatus.NOT_FOUND.value())
                            .build()
                    );
        }

        MyUser user = MyUser.builder()
            .login(request.getLogin())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .email(request.getEmail())
            .role(request.getRole())
            .date_registration(LocalDateTime.now())
            .build();

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        try {
            userService.save(user);
        } catch (ConstraintViolationException e) {
            error.append(e.getConstraintViolations()
                .stream()
                .toList()
                .getFirst()
                .getMessage());
            log.error(error.toString());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(patternCustom.registrationError(error.toString()))
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        } catch (DataIntegrityViolationException e) {
            error.append(e.getMostSpecificCause().getLocalizedMessage());
            log.error(error.toString());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(patternCustom.registrationError(error.toString()))
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        }

        try {
            jwt.append(jwtService.generateToken(new MyUserDetails(user)));
        } catch (JwtGenerateTokenExceptions e) {
            error.append("Ошибка генерации токена");
            log.error(error.toString());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JwtAuthenticationResponse
                            .builder()
                            .error(error.toString())
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        }

        return ResponseEntity.ok().body(
                JwtAuthenticationResponse
                        .builder()
                        .token(jwt.toString())
                        .code(HttpStatus.CREATED.value())
                        .build()
        );
    }
}
