package com.habilisadi.auth.application.port.`in`

import com.habilisadi.auth.application.dto.UserDetailCommand
import com.habilisadi.auth.shared.application.dto.ResponseStatus

interface UpdateUserProfileImageUseCase {
    fun updateUserProfileImage(command: UserDetailCommand.UpdateProfileImage): ResponseStatus<String>
}