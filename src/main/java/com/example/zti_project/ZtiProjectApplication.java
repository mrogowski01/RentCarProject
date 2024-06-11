package com.example.zti_project;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;

@SpringBootApplication
public class ZtiProjectApplication {

    public static void main(String[] args) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Wypisz wygenerowany klucz
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        SpringApplication.run(ZtiProjectApplication.class, args);
    }

}
