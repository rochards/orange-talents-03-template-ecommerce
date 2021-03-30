package br.com.zupacademy.desafiomercadolivre.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}") // busca la do meu application.properties
    private String expiration;

    public String geraToken(Authentication authentication) {

        var usuarioLogado = (User) authentication.getPrincipal();
        var tokenGeradoEm = new Date();
        var tokenExpiraEm = new Date(System.currentTimeMillis() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Api desafio do mercado livre")
                .setSubject(usuarioLogado.getUsername())
                .setIssuedAt(tokenGeradoEm)
                .setExpiration(tokenExpiraEm)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }
}
