package com.habilisadi.auth.domain.model

import jakarta.persistence.Embeddable
import org.springframework.security.crypto.password.PasswordEncoder

@Embeddable
data class Password(
    val value: String
) {
    init {
        require(value.isNotEmpty()) { "Password cannot be empty" }
        require(value.length >= 8) { "Password must be at least 8 characters long" }
    }

    companion object {
        fun createFromRawPassword(rawPassword: String, passwordEncoder: PasswordEncoder) =
            Password(passwordEncoder.encode(rawPassword))
    }
}
