package com.example.demo

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class TeaAuthentication(
    private var authenticated: Boolean = true,
    private var password: String?,
    private var name: String = "Mister Tea",
    private var authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER"))
) : Authentication {

    companion object {
        fun authenticated(unauthenticated: TeaAuthentication): TeaAuthentication {
            unauthenticated.apply {
                authenticated = true
                password = null
                name = "Tea Master"
                authorities = listOf(
                    SimpleGrantedAuthority("ROLE_TEA_MASTER"),
                    SimpleGrantedAuthority("ROLE_USER")
                )
            }
            return unauthenticated
        }

        fun unauthenticated(password: String): TeaAuthentication {
            return TeaAuthentication(
                authenticated = false,
                password = password,
                name = "Mister Tea",
                authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        }
    }

    override fun getName(): String = name

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getCredentials(): Any? = password

    override fun getDetails(): Any? = null

    override fun getPrincipal(): Any = name

    override fun isAuthenticated(): Boolean = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        // This method is required by the interface but we don't need to implement it
        // as our authentication state is set during construction
    }
}
