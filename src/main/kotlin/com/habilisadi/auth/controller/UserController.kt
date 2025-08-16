package com.habilisadi.auth.controller

import com.habilisadi.auth.dto.UserDto
import com.habilisadi.auth.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UserController(
    private val userService: UserService
) {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/register")
    fun register(): String {
        return "register"
    }

    @PostMapping("/register")
    fun register(
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam confirmPassword: String,
        model: Model
    ): String {
        return try {
            val userDto = UserDto(
                email = email,
                pwd = password,
                confirmPwd = confirmPassword
            )

            userService.save(userDto)
            model.addAttribute("success", true)
            "register"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            model.addAttribute("email", email)
            "register"
        }
    }

}