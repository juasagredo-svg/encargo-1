package com.galiana_project.cl.galiana_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Galiana API")
                        .version("1.0")
                        .description("Con esta API se puede administrar Galiana")
                        .contact(
                                new io.swagger.v3.oas.models.info.Contact()
                                        .name("Galiana Team")
                                        .email("luc.huerta@duocuc.cl")));
    }
}
