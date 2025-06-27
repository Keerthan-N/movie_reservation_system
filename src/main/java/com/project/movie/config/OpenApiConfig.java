package com.project.movie.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private SecurityScheme createApitScheme(){
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic");
    }

    @Bean
    OpenAPI appOpenApi(){

        Contact contact = new Contact();
        contact.setEmail("mcworkpurpose@gmail.com");
        contact.setName("Developer");
        contact.setUrl("https://www.google.com");

        Info info = new Info()
                .title("Movie Reservation Services")
                .version("1.0")
                .contact(contact)
                .description("Book Movie tickets online through api services")
                .termsOfService("https://www.google.com");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new Components().addSecuritySchemes("basicAuth", createApitScheme()))
                .info(info);
    }

}
