package com.example.demo

import org.springframework.security.core.Authentication
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
        val name = authentication.name ?: "Guest"
        return "This is a private endpoint 🔒 - Hello $name! Only authorized users should access it! ✅"
    }

}
