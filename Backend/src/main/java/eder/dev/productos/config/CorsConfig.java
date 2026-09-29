package eder.dev.productos.config;

// Permite llamadas SOLO desde el frontend (http://localhost:4200)

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // Se lee de application.properties (app.cors.allowed-origin)
    @Value("${app.cors.allowed-origin}")
    private String originPermitido;

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(originPermitido)
                .allowedMethods("GET", "POST", "PUT", "DELETE");

    }
}
