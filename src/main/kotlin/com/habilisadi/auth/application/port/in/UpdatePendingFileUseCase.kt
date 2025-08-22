package com.habilisadi.auth.application.port.`in`

import com.habilisadi.auth.application.dto.PendingFileCommand
import com.habilisadi.auth.application.dto.PendingFileResponse

interface UpdatePendingFileUseCase {
    fun update(command: PendingFileCommand.Update): PendingFileResponse.Update
}