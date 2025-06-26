package com.example.demo

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TeaHeaderFilter @Autowired constructor(
    private val authenticationManager: AuthenticationManager
) : OncePerRequestFilter() {

    companion object {
        private const val TEA_HEADER = "x-tea"
        private const val TEA_HEADER_VALUE = "yes"
        private const val TEA_PASSWORD_HEADER = "x-tea-password"
        private const val TEA_EMOTICON = "🍵"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val teaHeaderValue = request.getHeader(TEA_HEADER)
        val password = request.getHeader(TEA_PASSWORD_HEADER)

        if (TEA_HEADER_VALUE.equals(teaHeaderValue, ignoreCase = true)) {

            val unauthenticated = TeaAuthentication.unauthenticated(password)

            try {
                // Authenticate using the AuthenticationManager
                val authentication = authenticationManager.authenticate(unauthenticated)

                // Set the authenticated object in the SecurityContext
                SecurityContextHolder.getContext().authentication = authentication

                // Print a phrase with tea emoticon
                println("Time for tea! $TEA_EMOTICON Enjoy your brew, ${authentication.name}!")

                // Continue with the filter chain
                filterChain.doFilter(request, response)
            } catch (e: AuthenticationException) {
                // Authentication failed
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.writer.write("Invalid tea credentials! $TEA_EMOTICON")
            }
        } else {
            // Reject the request
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("No tea, no service! $TEA_EMOTICON")
        }
    }
}
