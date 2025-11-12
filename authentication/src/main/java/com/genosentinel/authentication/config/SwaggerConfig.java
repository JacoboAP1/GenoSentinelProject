package com.genosentinel.authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger / OpenAPI para la documentación del microservicio.
 * Permite acceder a una interfaz web interactiva en /swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("GenoSentinel - Autenticación API")
                        .description("Microservicio de autenticación y simulación de gateway para GenoSentinel")
                        .version("1.0.0"));
    }
}
