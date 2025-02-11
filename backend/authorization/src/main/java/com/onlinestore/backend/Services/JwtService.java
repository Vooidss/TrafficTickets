package com.onlinestore.backend.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.*;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.onlinestore.backend.Exceptions.JwtGenerateTokenExceptions;
import com.onlinestore.backend.Models.BlackListTokens;
import com.onlinestore.backend.Repositories.BlackListRepository;
import com.onlinestore.backend.user.MyUser;
import com.onlinestore.backend.user.MyUserDetails;

@Service
@Slf4j
public class JwtService {

    private final BlackListRepository blackListRepository;

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public JwtService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    /**
     * Извлечение логина пользователя из токена
     *
     * @param token токен
     * @return логин пользователя
     */
    public String extractLogin(String token) {
        if (token.isEmpty()) {
            throw new RuntimeException("Токен пустой");
        }

        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерация токена
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails)
        throws JwtGenerateTokenExceptions {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof MyUserDetails customUserDetails) {
            MyUser user = customUserDetails.getUser();

            claims.put("id", user.getId());
            claims.put("name", user.getName());
            claims.put("email", user.getEmail());
            claims.put("role", user.getRole());
        }
        return generateTokenWithExtraClaims(claims, userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String login = extractLogin(token);
        return (
            (login.equals(userDetails.getUsername())) && !isTokenExpired(token)
        );
    }

    private <T> T extractClaim(
        String token,
        Function<Claims, T> claimsResolvers
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токена
     *
     * @param extraClaims дополнительные данные
     * @param userDetails данные пользователя
     * @return токен
     */
    private String generateTokenWithExtraClaims(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ) {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    protected boolean isTokenExpired(String token) {
        try {
            extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private SecretKey getSigningKey() {
        byte[] key = Base64.getDecoder().decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(key);
    }

    public void saveInvalidateToken(BlackListTokens blackListTokens) {
        blackListRepository.save(blackListTokens);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackListRepository.findByToken(token).isPresent();
    }
}
