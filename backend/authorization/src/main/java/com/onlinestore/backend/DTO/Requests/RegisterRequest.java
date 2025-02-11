package com.onlinestore.backend.DTO.Requests;

import lombok.*;
import com.onlinestore.backend.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest{

    private String name;
    private String secondName;
    private String login;
    private String password;
    private String email;
    private Role role;
    private int age;
    private String phone;
    private String sex;
}
