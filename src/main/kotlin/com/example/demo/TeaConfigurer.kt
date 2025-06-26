package com.example.demo

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.access.intercept.AuthorizationFilter

class TeaConfigurer(private val teaAuthenticationProvider: TeaAuthenticationProvider? = null) 
    : AbstractHttpConfigurer<TeaConfigurer, HttpSecurity>() {

    companion object {
        fun tea(teaAuthenticationProvider: TeaAuthenticationProvider): TeaConfigurer = 
            TeaConfigurer(teaAuthenticationProvider)
    }

    override fun init(httpSecurity: HttpSecurity) {
        // Add the provider to the httpSecurity
        // If no provider was passed, create a new one
        val provider = teaAuthenticationProvider ?: TeaAuthenticationProvider()
        httpSecurity.authenticationProvider(provider)
    }

    override fun configure(httpSecurity: HttpSecurity) {
        // Get the AuthenticationManager shared object
        val authenticationManager = httpSecurity.getSharedObject(AuthenticationManager::class.java)

        // Create the TeaHeaderFilter with the AuthenticationManager
        val teaHeaderFilter = TeaHeaderFilter(authenticationManager)

        // Add the filter before the AuthorizationFilter
        httpSecurity.addFilterBefore(teaHeaderFilter, AuthorizationFilter::class.java)
    }
}
