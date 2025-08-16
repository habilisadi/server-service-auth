package com.habilisadi.auth.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(authentication: Authentication?, model: Model): String {
        if (authentication != null) {
            model.addAttribute("username", authentication.name)
            model.addAttribute("authorities", authentication.authorities)
        }
        return "home"
    }
}