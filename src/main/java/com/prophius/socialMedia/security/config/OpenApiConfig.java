package com.prophius.socialMedia.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {
    Contact james = new Contact()
            .name("James Aduloju")
            .email("lekan.sofuyi@outlook.com")
            .url("https://github.com/Jamezz5o9");

    @Bean
    public OpenAPI configAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Social Space Api")
                        .version("Version 1.00")
                        .description("The Spring Boot Task Application is a lightweight and efficient project " +
                                "designed to manage user post and comments. Built using the Spring Boot " +
                                "framework, this application provides a simple and intuitive application programming " +
                                "interface for users to create, read, update delete, and track their post and comments in a convenient manner.")
                        .contact(james)
                        .termsOfService("Coming Soon!!! "));
    }
}