package com.habilisadi.auth.adapter.user.`in`.web

import com.habilisadi.auth.application.user.dto.CreateUserCommand
import com.habilisadi.auth.application.user.port.`in`.CreateUserUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UserController(
    private val createUserUseCase: CreateUserUseCase
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
            val createUserCommand = CreateUserCommand.create(
                email,
                password,
                confirmPassword
            )

            createUserUseCase.createUser(createUserCommand)
            model.addAttribute("success", true)
            "register"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            model.addAttribute("email", email)
            "register"
        }
    }

}