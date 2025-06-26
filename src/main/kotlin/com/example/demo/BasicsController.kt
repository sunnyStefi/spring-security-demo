package com.example.demo

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BasicsController {

    @GetMapping("/public")
    fun publicEndpoint(): String {
        return "This is a public endpoint 🌍 - Everyone can access it! 🎉"
    }

    @GetMapping("/private")
    fun privateEndpoint(authentication: Authentication): String {
        val name = getName(authentication)
        return "This is a private endpoint 🔒 - Hello $name! Only authorized users should access it! 🚫"
    }

    private fun getName(authentication: Authentication): String {
         if (authentication is OAuth2AuthenticationToken) {
             val attributes = authentication.principal.attributes
             val name = attributes["given_name"] as? String ?: "OAuth2 User"
             return "$name 🔑"
         }
        return "${authentication.name} 👤"
    }
}
