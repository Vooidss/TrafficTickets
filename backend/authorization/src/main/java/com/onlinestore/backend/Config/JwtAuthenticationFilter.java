package com.onlinestore.backend.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.onlinestore.backend.Services.JwtService;
import com.onlinestore.backend.Services.MyUserDetailService;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull @NonNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String path = request.getRequestURI();

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userLogin = "";

        if (
            authHeader == null ||
            !authHeader.startsWith("Bearer ") ||
            path.startsWith("/auth")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Берем токен из заголовка Authorization...");

        jwt = authHeader.substring(7);

        log.info("Токен получен \n" + jwt);

        try {
            log.info("Берем логин из токена...");
            userLogin = jwtService.extractLogin(jwt);
            log.info("Логин получен: " + userLogin);
        } catch (RuntimeException e) {
            log.error("Ошибка получения Логина.");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Проверяем запрещён ли токен...");

        if (jwtService.isTokenBlacklisted(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Токен находится в Black List");
            return;
        }

        log.info("Токен разрешён!");

        log.info("Проверяем есть находиться ли в Security Context пользователь...");

        if (
            SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            log.info("Пользователь нет. Продолжаем!");

            log.info("Загружаем пользователя...");
            UserDetails userDetails =
                this.userDetailService.loadUserByUsername(userLogin);

            log.info("Пользователь загружен!");

            log.info("Проверяем валидный ли токен...");

            if (jwtService.isTokenValid(jwt, userDetails)) {
                log.info("Токен валидный!");

                log.info("Загружаем пользователя в Secutity Context");
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.info("Пользователь загружен!");
            } else {
                filterChain.doFilter(request, response);
                log.error("Токен не валидный");
            }
        }

        log.info("Токен прошёл фильтрацию");
        filterChain.doFilter(request, response);
    }
}
