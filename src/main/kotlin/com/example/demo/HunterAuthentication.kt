package com.example.demo

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class HunterAuthentication private constructor(
    private val credentials: String?,
    private val isAuthenticated: Boolean,
    private val principal: String?,
    private val hunterAuthorities: Collection<GrantedAuthority>,
) : Authentication {

    companion object {
        fun unauthenticated(password: String): HunterAuthentication {
            return HunterAuthentication(
                credentials = password,
                isAuthenticated = false,
                principal = "rickGrimes",
                hunterAuthorities = emptyList()
            )
        }

        fun authenticated(unauthenticated: HunterAuthentication): HunterAuthentication {
            val authorities = listOf(
                SimpleGrantedAuthority("ROLE_CITIZEN"),
                SimpleGrantedAuthority("ROLE_HUNTER"),
                SimpleGrantedAuthority("ROLE_SAFE_ZONE"),
            )

            return HunterAuthentication(
                credentials = null,
                isAuthenticated = true,
                principal = unauthenticated.principal,
                hunterAuthorities = authorities
            )
        }
    }

    override fun getName(): String? {
        return principal
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return hunterAuthorities
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    override fun isAuthenticated(): Boolean {
        return isAuthenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw UnsupportedOperationException("Cannot set authentication status directly. Use the authenticated() factory method instead.")
    }
}