package com.example.demo

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class SafeZoneSuccessHandler : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val hasHunterRole = authentication.authorities.any { auth ->
            auth.authority == "ROLE_HUNTER"
        }

        if (hasHunterRole) {
            response.sendRedirect("safe-zone")
        } else {
            response.sendRedirect("beach-zone")
        }
    }
}