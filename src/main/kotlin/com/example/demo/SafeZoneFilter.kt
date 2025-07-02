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
        private const val ROLE_HUNTER = "ROLE_HUNTER"
        private const val ROLE_SAFE_ZONE = "ROLE_SAFE_ZONE"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val safeZoneHeaderValue = request.getHeader(SAFE_ZONE_HEADER)
        val username = SecurityContextHolder.getContext().authentication.name

        val userDetails = try {
            userDetailsService.loadUserByUsername(username)
        } catch (ex: UsernameNotFoundException) {
            filterChain.doFilter(request, response)
            return
        }

        val hasHunterRole = userDetails.authorities.any { it.authority == ROLE_HUNTER }

        if (safeZoneHeaderValue == SAFE_ZONE_SECRET && hasHunterRole) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    userDetails.authorities + SimpleGrantedAuthority(ROLE_SAFE_ZONE)
                )

            filterChain.doFilter(request, response)
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("You'll not be safe here!! Run")
            return
        }

    }
}
