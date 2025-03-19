package uk.ac.standrews.cs.host.cs3099user20.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowCredentials(true).allowedOrigins("https://cs3099user20.host.cs.st-andrews.ac.uk").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
        registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://localhost:3000");
//        registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://localhost:23417");
//        registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://127.0.0.1:23417");
    }
}
