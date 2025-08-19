package com.habilisadi.auth.application.file.dto

import com.habilisadi.auth.domain.file.model.Status
import com.habilisadi.auth.domain.user.model.Email

class PendingFileCommand {
    data class Create(
        val email: Email,
        val fileName: String,
    )

    class Update(
        val email: Email,
        val pendingId: String,
        val status: Status
    )
}