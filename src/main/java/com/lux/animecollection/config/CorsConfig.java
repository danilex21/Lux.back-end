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
        
        // Allow all origins, methods, and headers for development
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8081"); // Your frontend URL
        config.addAllowedOrigin("http://localhost:3000"); // Common React dev server port
        config.addAllowedOrigin("https://anime-collection-nf6r.onrender.com"); // Your backend URL
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}