package com.habilisadi.auth.application.user.port.`in`

import com.habilisadi.auth.application.user.dto.UpdateUserProfileCommand
import com.habilisadi.auth.common.dto.ResponseStatus

interface UpdateUserProfileImageUseCase {
    fun updateUserProfileImage(command: UpdateUserProfileCommand): ResponseStatus<String>
}