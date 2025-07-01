package com.example.demo

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ZoneController{

    @GetMapping("/safe-zone")
    fun safeZone(): String {
        return "safe-zone"
    }

    @GetMapping("/zombie-zone")
    fun zombieZone(): String {
        return "zombie-zone"
    }
}