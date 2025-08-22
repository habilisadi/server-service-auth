package com.habilisadi.auth.application.port.`in`

import com.habilisadi.auth.application.dto.PendingFileCommand
import com.habilisadi.auth.application.dto.PendingFileResponse

interface SavePendingFileUseCase {
    fun savePendingRequests(command: PendingFileCommand.Save): PendingFileResponse.Save
}