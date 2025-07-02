package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SafeZoneController{

    @GetMapping("/safe-zone")
    fun safeZone(): String {
        return "safe-zone"
    }

    @GetMapping("/beach-zone")
    fun beachZone(): String {
        return "beach-zone"
    }
}