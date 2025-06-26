package com.example.demo

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class SupermanAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication? {
        val username = authentication.name
        
        // Check if the username is "clark" or "superman"
        return if (username == "clark" || username == "superman") {
            // Create an authenticated token with ROLE_ADMIN
            val authorities = listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
            UsernamePasswordAuthenticationToken(username, null, authorities)
        } else {
            // Return null for other usernames
            null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}