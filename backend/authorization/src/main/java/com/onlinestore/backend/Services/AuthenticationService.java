package com.onlinestore.backend.Services;

import com.onlinestore.backend.user.MyUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.onlinestore.backend.DTO.Requests.AuthenticationRequest;
import com.onlinestore.backend.DTO.Response.JwtAuthenticationResponse;
import com.onlinestore.backend.Exceptions.JwtGenerateTokenExceptions;
import com.onlinestore.backend.Models.BlackListTokens;
import com.onlinestore.backend.user.MyUserDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public ResponseEntity<JwtAuthenticationResponse> authenticate(AuthenticationRequest request) {
        StringBuilder jwt = new StringBuilder();

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getLogin(),
                    request.getPassword()
                )
            );
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok().body(JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                            null,
                            true,
                            "Неверный логин или пароль",
                            HttpStatus.NOT_FOUND.value(),
                            true
                    )
            );
        }

        MyUser user = userService.getByLogin(request.getLogin());

        if(user == null){
            return ResponseEntity.ok().body(JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                            null,
                            true,
                            "Пользователь с таким логином не найден",
                            HttpStatus.NOT_FOUND.value(),
                            true
                    )
            );
        }

        MyUserDetails myUserDetails = MyUserDetails.builder()
                .user(user)
                .build();

        try {
            jwt.append(jwtService.generateToken(myUserDetails));
        } catch (JwtGenerateTokenExceptions e) {
            log.error(e.getMessage());
            return ResponseEntity.ok().body(JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                            null,
                            true,
                            "Ошибка генерации токена",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            true
                    )
            );
        }

        return ResponseEntity.ok().body(JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                jwt.toString(),
                jwtService.isTokenExpired(jwt.toString()),
                null,
                HttpStatus.OK.value(),
                false
            )
        );
    }

    public ResponseEntity<JwtAuthenticationResponse> isTokenExpired(
        HttpServletRequest request
    ) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return ResponseEntity.ok(
                JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                    null,
                    true,
                    "Пользователь ещё не вошёл.",
                    401,
                    true
                )
            );
        }

        String token = request.getHeader("Authorization").substring(7);

        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.ok(
                JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                    token,
                    true,
                    null,
                    200,
                    false
                )
            );
        }

        return ResponseEntity.ok(
            JwtAuthenticationResponse.generateJwtAuthenticationResponse(
                token,
                false,
                null,
                200,
                false
            )
        );
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);

        jwtService.saveInvalidateToken(
            BlackListTokens.builder().token(token).build()
        );

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout successful");
    }
}
