package com.genosentinel.authentication.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para el módulo de autenticación.
 * Define la información general de la API y registra el esquema de seguridad
 * basado en JWT para habilitar el botón "Authorize" en la interfaz de Swagger
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Definición del esquema de autenticación Bearer con JWT
        // para permitir que Swagger envíe el token en el header Authorization
        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearer-jwt")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Introduce el token en formato: Bearer {token}");

        return new OpenAPI()
                .info(new Info()
                        .title("Genosentinel - Servicio de Autenticación")
                        .description("API de autenticación con JWT")
                        .version("1.0.0")
                )
                // Agregamos el esquema de autenticación; no forzamos su uso en todos los endpoints
                .components(new Components().addSecuritySchemes("bearer-jwt", securityScheme));
    }
}