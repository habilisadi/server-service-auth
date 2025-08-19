package com.habilisadi.auth.application.file.dto

import com.habilisadi.auth.domain.file.model.FileName
import com.habilisadi.auth.domain.file.model.FilePath
import com.habilisadi.auth.domain.file.model.PendingFileEntity
import com.habilisadi.auth.domain.user.model.Email

class PendingFileResponse {

    data class Create(
        val pendingId: String,
        val fileName: String,
        val filePath: String,
    ) {
        companion object {
            fun from(pendingFileEntity: PendingFileEntity): Create {
                if (pendingFileEntity.id == null) {
                    throw IllegalArgumentException("PendingFileEntity id is null")
                }

                return Create(
                    pendingId = pendingFileEntity.id!!,
                    fileName = pendingFileEntity.fileName.value,
                    filePath = pendingFileEntity.filePath.value,
                )
            }
        }
    }

    data class Update(
        val filePath: FilePath,
        val fileName: FileName,
        val email: Email
    ) {
        companion object {
            fun from(pendingFileEntity: PendingFileEntity, email: Email): Update {
                return Update(
                    filePath = pendingFileEntity.filePath,
                    fileName = pendingFileEntity.fileName,
                    email = email
                )
            }
        }
    }
}