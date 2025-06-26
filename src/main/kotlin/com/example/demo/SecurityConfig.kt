package com.example.demo

import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val supermanAuthenticationProvider: SupermanAuthenticationProvider,
                     private val teaAuthenticationProvider: TeaAuthenticationProvider,
                     private val userDetailsService: UserDetailsService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/private").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults())
            .with(TeaConfigurer.tea(teaAuthenticationProvider), Customizer.withDefaults())
            .userDetailsService(userDetailsService)
        return http.build()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(supermanAuthenticationProvider, teaAuthenticationProvider))
    }

    @Bean
    fun authenticationSuccessListener(): ApplicationListener<AuthenticationSuccessEvent> =
        ApplicationListener { event -> println("Successfully authenticated with ${event.authentication.javaClass.simpleName} 🎉") }

}
