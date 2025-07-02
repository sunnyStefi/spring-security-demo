package com.example.demo

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SafeZoneFilter(
    private val authenticationProvider: HunterAuthenticationProvider
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

        if (safeZoneHeaderValue != SAFE_ZONE_SECRET) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid safe zone secret")
            return
        }

        val unauthenticatedEntity = HunterAuthentication.unauthenticated(safeZoneHeaderValue)

        try {
            val authentication = authenticationProvider.authenticate(unauthenticatedEntity)

            SecurityContextHolder.getContext().authentication = authentication

            filterChain.doFilter(request, response)
        } catch (ex: AuthenticationException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
        }
    }
}
