package com.onlinestore.backend.DTO.Response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class DefaultResponse {
    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String message;
}
