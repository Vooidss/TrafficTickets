package com.onlinestore.backend.Controllers;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlinestore.backend.DTO.Requests.UpdateRequest;
import com.onlinestore.backend.Services.UserService;
import com.onlinestore.backend.user.MyUser;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<Map<String, Integer>> findUserIdByLogin() {
        return userService.findUserId();
    }

    @GetMapping("/current")
    public ResponseEntity<MyUser> getResponseCurrentUser() {
        return userService.getResponseCurrentUser();
    }

    @PatchMapping("/update")
    public ResponseEntity<Map<String,Object>> updateUser(@RequestBody UpdateRequest newUser){
        return userService.updateUser(newUser);
    }
}
