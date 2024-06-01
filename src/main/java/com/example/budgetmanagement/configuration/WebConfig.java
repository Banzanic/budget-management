package com.example.budgetmanagement.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String chartDir = Paths.get("src/main/resources/charts/").toUri().toString();
        registry.addResourceHandler("/charts/**")
                .addResourceLocations(chartDir)
                .setCacheControl(CacheControl.noStore());
    }
}
