package com.revined.revined.config;

import com.revined.revined.utils.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environments environments;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = Objects.equals(environments.getVariable("ENVIRONMENT"), "local") ?
                new String[]{"*"} :
                new String[]{"http://localhost:8080", "https://www.rackd.io/"};

        registry
                .addMapping("/**")
                .allowedOrigins(origins);
    }
}
