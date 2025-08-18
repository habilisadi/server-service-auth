package com.habilisadi.auth.application.user.dto

import jakarta.validation.constraints.NotBlank

data class UpdateUserProfileImageRequest(
    val id: Long,
    @field:NotBlank(message = "filename is required")
    val filename: String,
) {
    fun toCommand() = UpdateUserProfileCommand(
        id,
        filename
    )
}