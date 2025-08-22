package com.habilisadi.auth.application.port.`in`

import com.habilisadi.auth.application.dto.UserCommand
import com.habilisadi.auth.shared.application.dto.ResponseStatus

interface CreateUserUseCase {
    fun createUser(command: UserCommand.Create): ResponseStatus<Boolean>
}