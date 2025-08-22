package com.habilisadi.auth.application.dto

import com.habilisadi.auth.domain.model.UserEntity
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

class UserDetailRequest {
    data class Update(
        @field:Positive(message = "ID must be a positive number")
        val id: Long,
        val name: String,
        @field:Size(min = 10, max = 15)
        @field:Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
        val phone: String,
    ) {
        fun toCommand(user: UserEntity) = UserDetailCommand.Update(
            id,
            name,
            phone,
            user.id!!,
        )
    }
}