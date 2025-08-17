package com.habilisadi.auth.application.user.port.`in`

import com.habilisadi.auth.application.user.dto.CreateUserCommand
import com.habilisadi.auth.common.dto.ResponseStatus

interface CreateUserUseCase {
    fun createUser(command: CreateUserCommand): ResponseStatus<Boolean>
}