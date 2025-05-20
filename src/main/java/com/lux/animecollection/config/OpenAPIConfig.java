package com.lux.animecollection.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openapi.dev-url:http://localhost:8080}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor de desenvolvimento");

        Contact contact = new Contact();
        contact.setName("Lux - Anime Collection");
        contact.setEmail("srtoddy.surf@gmail.com");
        contact.setUrl("Em desenvolvimento");

        License license = new License()
                .name("Licença da API")
                .url("Em desenvolvimento");

        Info info = new Info()
                .title("API de Gerenciamento de Coleção de Animes")
                .version("1.0")
                .description("Esta API fornece endpoints para gerenciar uma coleção de animes.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
