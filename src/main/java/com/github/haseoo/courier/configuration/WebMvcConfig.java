package com.github.haseoo.courier.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.github.haseoo.courier.utilities.Constants.MAX_AGE_SECS;
import static com.github.haseoo.courier.utilities.Constants.corsAllowedMethods;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "http://dawidhomeserver.ddns.net/")
                .allowedMethods(corsAllowedMethods())
                .maxAge(MAX_AGE_SECS);
    }
}
