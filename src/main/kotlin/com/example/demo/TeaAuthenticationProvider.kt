package com.example.demo

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class TeaAuthenticationProvider : AuthenticationProvider {

    companion object {
        private const val CORRECT_PASSWORD = "teaTime"
    }

    override fun authenticate(authentication: Authentication): Authentication? {
        // Only process TeaAuthentication objects
        if (authentication !is TeaAuthentication) {
            return null
        }

        val password = authentication.credentials as String

        // Check if the password is correct
        return if (password == CORRECT_PASSWORD) {
            // Create an authenticated TeaAuthentication
            TeaAuthentication.authenticated(authentication)
        } else {
            // Return unauthenticated TeaAuthentication for incorrect password
            TeaAuthentication.unauthenticated(password)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return TeaAuthentication::class.java.isAssignableFrom(authentication)
    }
}
