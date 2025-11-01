package com.malkollm.childjobbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Применить ко всем путям, начинающимся с /api/
                .allowedOrigins("http://localhost:8081") // Разрешить запросы с этого origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Очень важно: включить OPTIONS
                .allowedHeaders("*") // Разрешить все заголовки
                .allowCredentials(true); // Если нужно, обычно не критично для GET
    }
}