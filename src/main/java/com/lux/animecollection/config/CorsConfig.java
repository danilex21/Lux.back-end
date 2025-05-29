package com.lux.animecollection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permite requisições de qualquer origem (em produção, especifique as origens permitidas)
        config.addAllowedOrigin("*");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:8081");
        
        // Permite todos os métodos HTTP
        config.addAllowedMethod("*");
        
        // Permite todos os cabeçalhos
        config.addAllowedHeader("*");
        
        // Permite credenciais (cookies, autenticação)
        config.setAllowCredentials(true);
        
        // Configura o tempo máximo que a resposta de preflight pode ser armazenada em cache
        config.setMaxAge(3600L);
        
        // Aplica a configuração a todos os endpoints
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
