package com.tickets.DTO.Response;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
public abstract class Response {
    private String message;
    private HttpStatus status;
    private Integer code;
}
