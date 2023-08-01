package com.example.marketcustomer.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowedOrigins(
                "http://localhost:3000",
                "http://localhost:3001"
            )
    }
}
