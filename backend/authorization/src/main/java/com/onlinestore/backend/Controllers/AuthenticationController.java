package com.onlinestore.backend.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlinestore.backend.DTO.Requests.AuthenticationRequest;
import com.onlinestore.backend.DTO.Response.JwtAuthenticationResponse;
import com.onlinestore.backend.Services.AuthenticationService;

@RestController
@RequestMapping("/auth/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<JwtAuthenticationResponse> authentication(
        @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }

    @GetMapping("/isTokenExpired")
    public ResponseEntity<JwtAuthenticationResponse> isValidToken(
        HttpServletRequest request
    ) {
        return authenticationService.isTokenExpired(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }
}
