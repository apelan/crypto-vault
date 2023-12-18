package com.cryptovaultdoo.cryptovault.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/api").description("Default server URL"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer authorization"))
                .components(new Components()
                        .addSecuritySchemes("Bearer authorization",
                                new SecurityScheme()
                                        .name("Scheme")
                                        .description("Input valid JWT token")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Crypto Vault")
                        .version("1.0.0"));
    }

}
