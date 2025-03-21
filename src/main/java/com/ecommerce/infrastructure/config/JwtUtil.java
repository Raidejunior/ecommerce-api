package com.ecommerce.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Chave secreta de 256 bits em Base64 para HMAC SHA
    private static final String SECRET_KEY = "B9C46FB3F332BB3E113ACE03FC37F4964F0DDAA1062CAF1E95F0DDAA1062CAF1E95"; // 256 bits
    private static final long EXPIRATION_TIME = 3600 * 1000; // 1 hora (3600000 ms)

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(String nomeUsuario) {
        return Jwts.builder()
                .subject(nomeUsuario) // Define o usuário como "sub"
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey()) // Assina com chave HMAC256
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build();
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        return claimsJws.getBody();
    }

    public boolean isTokenValid(String token, String nomeUsuario) {
        final String username = extractUsername(token);
        return (username.equals(nomeUsuario) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
