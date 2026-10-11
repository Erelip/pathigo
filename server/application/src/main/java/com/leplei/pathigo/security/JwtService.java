package com.leplei.pathigo.security;

import com.leplei.pathigo.models.AuthTokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    private final SecretKey signingKey;
    private final long accessExpirationMillis;
    private final long refreshExpirationMillis;

    public JwtService(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.access.expiration-ms}") long accessExpirationMillis,
            @Value("${security.jwt.refresh.expiration-ms}") long refreshExpirationMillis
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMillis = accessExpirationMillis;
        this.refreshExpirationMillis = refreshExpirationMillis;
    }

    public AuthTokens generateToken(String username) {
        Date now = new Date();
        Date accessExpiry = new Date(now.getTime() + accessExpirationMillis);
        Date refreshExpiry = new Date(now.getTime() + refreshExpirationMillis);

        var accessToken = Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(accessExpiry)
                .signWith(signingKey)
                .compact();

        var refreshToken = Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(refreshExpiry)
                .signWith(signingKey)
                .compact();

        return new AuthTokens(accessToken, refreshToken);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String expectedSubject) {
        return extractUsername(token).equals(expectedSubject) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

}