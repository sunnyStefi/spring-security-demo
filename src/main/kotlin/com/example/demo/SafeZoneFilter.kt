package com.example.demo

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SafeZoneFilter(
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    companion object {
        private const val SAFE_ZONE_HEADER = "x-safe-zone"
        private const val SAFE_ZONE_SECRET = "123"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val safeZoneHeaderValue = request.getHeader(SAFE_ZONE_HEADER)
        val authenticationName = SecurityContextHolder.getContext().authentication?.name

        if (authenticationName.isNullOrEmpty()) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Missing user ⛔️")
            return
        }

        val userDetails = try {
            userDetailsService.loadUserByUsername(authenticationName)
        } catch (ex: UsernameNotFoundException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Unknown user ⛔️")
            return
        }

        val hasHunterRole = userDetails.authorities.any { it.authority == "ROLE_HUNTER" }

        if (safeZoneHeaderValue == SAFE_ZONE_SECRET && hasHunterRole) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    authenticationName,
                    null,
                    userDetails.authorities + SimpleGrantedAuthority("ROLE_SAFE_ZONE")
                )

            filterChain.doFilter(request, response)
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("You are not authorized to access the safe zone ⛔️")
        }
    }
}
