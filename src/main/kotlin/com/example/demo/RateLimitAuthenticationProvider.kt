package com.example.demo

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class RateLimitAuthenticationProvider : AuthenticationProvider {

    // Map to store the last authentication time for each username
    private val lastAuthenticationTimes = mutableMapOf<String, Instant>()

    override fun authenticate(authentication: Authentication): Authentication? {
        // Only process UsernamePasswordAuthenticationToken objects
        if (authentication !is UsernamePasswordAuthenticationToken) {
            return null
        }

        val username = authentication.name
        val currentTime = Instant.now()

        // Check if the user has authenticated within the last minute
        val lastAuthTime = lastAuthenticationTimes[username]
        if (lastAuthTime != null && lastAuthTime.isAfter(currentTime.minus(1, ChronoUnit.MINUTES))) {
            throw RateLimitAuthenticationException("Rate limit exceeded. Please try again later.")
        }

        // Update the last authentication time
        lastAuthenticationTimes[username] = currentTime

        // Delegate to the next authentication provider in the chain
        return null
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}

/**
 * Custom exception for rate limit violations
 */
class RateLimitAuthenticationException(message: String) : AuthenticationException(message)