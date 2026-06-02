package com.example.projectlogin.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "MiClaveSecretaUltraSeguraQueNadiePuedeAdivinar";

    public String crearToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 900000))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String validarTokenYObtenerUsuario(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}