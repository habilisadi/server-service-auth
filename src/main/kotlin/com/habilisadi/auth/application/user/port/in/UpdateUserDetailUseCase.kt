package com.habilisadi.auth.application.user.port.`in`

import com.habilisadi.auth.application.user.dto.UpdateUserDetailCommand
import com.habilisadi.auth.common.dto.ResponseStatus

interface UpdateUserDetailUseCase {
    fun updateUserDetail(command: UpdateUserDetailCommand): ResponseStatus<Boolean>
}