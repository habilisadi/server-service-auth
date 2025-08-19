package com.habilisadi.auth.application.user.port.`in`

import com.habilisadi.auth.application.user.dto.UserCommand
import com.habilisadi.auth.common.dto.ResponseStatus

interface CreateUserUseCase {
    fun createUser(command: UserCommand.Create): ResponseStatus<Boolean>
}