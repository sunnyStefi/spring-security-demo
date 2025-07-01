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
        val rickGrimes = User.withUsername("rickGrimes")
            .password("{noop}sheriff123")
            .roles("HUNTER", "LEADER")
            .build()

        val darylDixon = User.withUsername("darylDixon")
            .password("{noop}crossbow456")
            .roles("HUNTER")
            .build()

        return InMemoryUserDetailsManager(rickGrimes, darylDixon)
    }
}