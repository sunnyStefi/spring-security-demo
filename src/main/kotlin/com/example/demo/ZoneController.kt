package com.example.demo

import org.springframework.security.core.Authentication
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
        val name = authentication.name ?: "Citizen"
        return "🏡Secure bunker access granted to $name. ✅ Only trusted survivors may enter. "
    }

}