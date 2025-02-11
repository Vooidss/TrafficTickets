package com.onlinestore.backend.DTO.Requests;

import lombok.Data;
import com.onlinestore.backend.user.Role;

@Data
public abstract class Request {

    private String name;
    private String secondName;
    private String login;
    private String password;
    private String email;
    private Role role;
    private int age;
    private String phone;
    private int money;
    private String sex;
}
