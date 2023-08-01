package com.example.marketprovider.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    protected fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.POST, "/**").permitAll()

        return http.build()
    }
}