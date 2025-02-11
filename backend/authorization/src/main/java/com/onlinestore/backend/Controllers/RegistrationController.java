package com.onlinestore.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlinestore.backend.DTO.Response.JwtAuthenticationResponse;
import com.onlinestore.backend.DTO.Requests.RegisterRequest;
import com.onlinestore.backend.Services.RegistrationService;

@RestController
@RequestMapping("/auth/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<JwtAuthenticationResponse> createUser(
        @RequestBody RegisterRequest request
    ) {
        return registrationService.registration(request);
    }
}
