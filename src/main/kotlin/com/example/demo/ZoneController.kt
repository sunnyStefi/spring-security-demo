package com.example.demo

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ZoneController {

    @GetMapping("/public")
    fun publicEndpoint(): String {
        return "🧟‍♂️ Welcome to the public zone! This area is open for all survivors. Stay alert! ⚠️"
    }

    @GetMapping("/private")
    fun privateEndpoint(authentication: Authentication): String {
        val name = getName(authentication)
        return "🏡Secure bunker access granted to $name. ✅ Only trusted survivors may enter. "
    }

    private fun getName(authentication: Authentication): String {
        // val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is OAuth2AuthenticationToken) {
            val attributes = authentication.principal.attributes
            return attributes["given_name"] as String
        }
        return "${authentication.name} 👤"
    }

}
