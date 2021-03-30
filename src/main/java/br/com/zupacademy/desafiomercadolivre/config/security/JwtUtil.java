package br.com.zupacademy.desafiomercadolivre.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}") // busca la do meu application.properties
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String geraToken(Authentication authentication) {

        var usuarioLogado = (User) authentication.getPrincipal();
        var tokenGeradoEm = new Date();
        var tokenExpiraEm = new Date(System.currentTimeMillis() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Api desafio do mercado livre")
                .setSubject(usuarioLogado.getUsername())
                .setIssuedAt(tokenGeradoEm)
                .setExpiration(tokenExpiraEm)
                .signWith(geraChave(secret))
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(geraChave(secret))
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getLoginUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(geraChave(secret))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key geraChave(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
