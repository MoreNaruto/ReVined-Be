package com.revined.revined.config;

import com.revined.revined.utils.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
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
                new String[]{"http://localhost:8080", "https://www.rackd.io", "https://re10shon-backend.herokuapp.com/*"};

        registry
                .addMapping("/**")
                .allowedHeaders(
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ACCEPT,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                        HttpHeaders.ORIGIN,
                        "X-Requested-With",
                        "X-XSRF-TOKEN"
                )
                .allowedOrigins(origins)
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                .allowCredentials(!Objects.equals(environments.getVariable("ENVIRONMENT"), "local"));
    }
}
