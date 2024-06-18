package com.insight.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) settings.
 */
@Configuration
public class WebConfig {

    @Autowired
    private Environment env;

    /**
     * Configures CORS settings based on application properties.
     * 
     * @return a WebMvcConfigurer instance with CORS settings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Retrieve the allowed origins from the application properties. If not set, default to "*".
                String allowedOrigins = env.getProperty("cors.urls", "*");
                
                // Configure CORS settings
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
