package com.onlinestore.backend.DTO.Requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest{

    private String name;
    private String secondname;
    private int age;
    private String phone;
    private String sex;
}
