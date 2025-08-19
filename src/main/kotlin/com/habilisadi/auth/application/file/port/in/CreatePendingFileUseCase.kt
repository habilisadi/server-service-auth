package com.habilisadi.auth.application.file.port.`in`

import com.habilisadi.auth.application.file.dto.PendingFileCommand
import com.habilisadi.auth.application.file.dto.PendingFileResponse

interface CreatePendingFileUseCase {
    fun createPendingFile(command: PendingFileCommand.Create): PendingFileResponse.Create
}