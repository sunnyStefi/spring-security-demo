package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity, safeZoneFilter: SafeZoneFilter): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                c -> c.requestMatchers("/private").authenticated()
                .anyRequest().permitAll()
            }
            .httpBasic(Customizer.withDefaults())
            .addFilterAfter(safeZoneFilter, BasicAuthenticationFilter::class.java)
            .oauth2Login(Customizer.withDefaults())
        return http.build()
    }
}