package com.malkollm.childjobbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class ChildJobBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChildJobBackendApplication.class, args);

                SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512); // Генерирует безопасный ключ для HS512
                String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
                System.out.println("БЕЗОПАСНЫЙ JWT_SECRET: " + encodedKey);
    }

}
