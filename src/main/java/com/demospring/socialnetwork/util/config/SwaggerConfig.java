package com.demospring.socialnetwork.util.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", new SecurityScheme().bearerFormat("JWT").scheme("bearer").type(SecurityScheme.Type.HTTP)))
                .info(new Info().title("My REST API Social Network")
                        .description("Create for implemeation of my rest api")
                        .version("1.0").contact(new Contact().name("Khoi Nguyen")
                                .email( "buikhoinguyen2001@gmail.com").url("buikhoinguyen2001@gmail.com"))
                        .license(new License().name("License of API")
                                .url("My Lisence")));
    }
}
