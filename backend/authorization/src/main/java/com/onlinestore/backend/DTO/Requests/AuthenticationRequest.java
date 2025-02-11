package com.onlinestore.backend.DTO.Requests;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest{

    private String login;
    private String password;
}
