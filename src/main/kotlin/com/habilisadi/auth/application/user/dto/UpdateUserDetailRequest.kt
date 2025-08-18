package com.habilisadi.auth.application.user.dto

import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class UpdateUserDetailRequest(
    val id: Long,
    val name: String,
    @field:Length(min = 10, max = 15)
    @field:Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    val phone: String,
) {
    fun toCommand() = UpdateUserDetailCommand(
        id,
        name,
        phone,
    )
}
