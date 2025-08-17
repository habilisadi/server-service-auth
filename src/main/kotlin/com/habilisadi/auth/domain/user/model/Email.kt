package com.habilisadi.auth.domain.user.model

import jakarta.persistence.Embeddable

@Embeddable
data class Email(
    val value: String
) {
    init {
        require(value.isNotEmpty()) { "Email cannot be empty" }
        require(value.matches(EMAIL_REGEX)) { "Invalid email format" }
    }

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
    }

}

