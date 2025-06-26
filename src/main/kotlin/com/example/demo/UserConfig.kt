package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class UserConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = User.builder()
            .username("banana")
            .password("{noop}123")
            .roles("USER")
            .build()
        
        return InMemoryUserDetailsManager(userDetails)
    }
}