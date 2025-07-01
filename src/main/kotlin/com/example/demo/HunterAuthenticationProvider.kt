package com.example.demo

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class HunterAuthenticationProvider(val userDetailsService: UserDetailsService): AuthenticationProvider {

    companion object {
        private const val SAFE_ZONE_ROLE = "ROLE_HUNTER"
    }

    override fun authenticate(authentication: Authentication): Authentication? {
        val hunterAuthentication = authentication as HunterAuthentication

        val hunterDetails = userDetailsService.loadUserByUsername(authentication.name)

        val hasSafeZoneRole = hunterDetails.authorities.any { it.authority == SAFE_ZONE_ROLE }

        if (!hasSafeZoneRole) {
            throw BadCredentialsException("User does not have required role: $SAFE_ZONE_ROLE")
        }

        return HunterAuthentication.authenticated(hunterAuthentication)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return HunterAuthentication::class.java.isAssignableFrom(authentication)
    }
}
