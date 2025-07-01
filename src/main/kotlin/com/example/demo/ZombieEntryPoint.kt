package com.example.demo

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class ZombieEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.setHeader("X-not-allowed", "Zombies and Enemies are not allowed to enter the safe zone")
        response?.sendError(HttpStatus.UNAUTHORIZED.value())
    }
}
