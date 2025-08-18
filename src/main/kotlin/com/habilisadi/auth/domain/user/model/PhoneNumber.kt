package com.habilisadi.auth.domain.user.model

import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    var value: String
) {
    init {
        require(validate(value)) { "Invalid phone number format" }
    }

    companion object {
        val phoneRegex = Regex("^\\+?[0-9]{10,15}$")

        fun validate(phone: String): Boolean {
            return phoneRegex.matches(phone)
        }
    }

    fun update(phone: String) {
        require(validate(phone)) { "Invalid phone number format" }
        this.value = phone
    }


}
