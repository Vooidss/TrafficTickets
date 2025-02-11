package com.onlinestore.backend.Services;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.onlinestore.backend.DTO.Requests.UpdateRequest;
import com.onlinestore.backend.Repositories.UserRepositories;
import com.onlinestore.backend.user.MyUser;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepositories userRepository;
    private final ModelMapper modelMapper;

    public void save(MyUser user)
        throws ConstraintViolationException, DataIntegrityViolationException, UnexpectedTypeException {
        userRepository.save(user);
    }

    public MyUser getByLogin(String login) {
        return userRepository
            .findByLogin(login)
            .orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден")
            );
    }

    public MyUser getCurrentUser() {
        var username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        log.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication()));
        return getByLogin(username);
    }

    public ResponseEntity<MyUser> getResponseCurrentUser() {
        return ResponseEntity.ok(getCurrentUser());
    }

    public ResponseEntity<Map<String, Integer>> findUserId() {
        MyUser myUser = getCurrentUser();

        return ResponseEntity.ok(Map.of("Id", myUser.getId()));
    }

    private void addOtherData(MyUser myUser){
        myUser.setRole(getCurrentUser().getRole());
        myUser.setLogin(getCurrentUser().getLogin());
        myUser.setEmail(getCurrentUser().getEmail());
        myUser.setPassword(getCurrentUser().getPassword());
        myUser.setDate_registration(getCurrentUser().getDate_registration());
    }

    public  ResponseEntity<Map<String,Object>> updateUser(UpdateRequest updateRequest) {
        System.out.println(updateRequest);
        log.info("Получаем id залогированного пользователя...");

        int id = getCurrentUser().getId();

        log.info("id {} получен!",id);

        log.info("Проверяем существует ли пользователь с таким id в базе данных...");

        if(userRepository.existsById(id)){
            log.info("Пользователь существует!");
            log.info("Сохраняем пользователя...");

            Optional<MyUser> updateUser;

            MyUser myUser = modelMapper.map(updateRequest, MyUser.class);
            myUser.setId(id);
            addOtherData(myUser);

            try {
                updateUser = Optional.of(userRepository.save(myUser));
            }catch (RuntimeException e){
                log.error(e.toString());
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
                log.error("Ошибка обновления пользователя");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "user", "",
                                "code", HttpStatus.NOT_FOUND.value(),
                                "status", HttpStatus.NOT_FOUND,
                                "message", "Ошибка обновления"
                        ));
            }
            log.info("Пользователь сохранён!");

            return ResponseEntity.ok().body(
                    Map.of(
                            "user", updateUser,
                            "code", HttpStatus.OK.value(),
                            "status", HttpStatus.OK,
                            "message", "Данные пользователя обновлены"
                    ));
        }else{
            log.error("Пользователя с таким ID не существует. :(");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "user","",
                            "code",HttpStatus.NOT_FOUND.value(),
                            "status",HttpStatus.NOT_FOUND,
                            "message","Пользователя не найден"
                    ));
        }
    }
}
