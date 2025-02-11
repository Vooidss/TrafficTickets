package com.onlinestore.backend.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class JwtAuthenticationResponse {

    @Schema(
        description = "Токен доступа",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj..."
    )
    private String token;

    private String error;
    private int code;
    private boolean isFailed;
    private boolean isTokenExpired;

    public JwtAuthenticationResponse setError(String error) {
        this.error = error;
        return this;
    }

    public static JwtAuthenticationResponse generateJwtAuthenticationResponse(
        String token,
        boolean isTokenExpired,
        String error,
        int code,
        boolean isFailed
    ) {
        return JwtAuthenticationResponse.builder()
            .token(token)
            .isTokenExpired(isTokenExpired)
            .error(error)
            .code(code)
            .isFailed(isFailed)
            .build();
    }
}
