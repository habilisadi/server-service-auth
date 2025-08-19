package com.habilisadi.auth.application.file.dto

import com.habilisadi.auth.domain.file.model.Status
import com.habilisadi.auth.domain.user.model.UserEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


class PendingFileRequest {

    data class Create(
        @field:NotNull(message = "파일명은 필수입니다.")
        @field:NotBlank(message = "파일명은 필수입니다.")
        val fileName: String,
    ) {
        fun toCommand(user: UserEntity): PendingFileCommand.Create {
            if (fileName.isBlank()) throw IllegalArgumentException("파일명은 필수입니다.")

            return PendingFileCommand.Create(
                user.email,
                fileName = fileName,
            )
        }
    }

    data class Update(
        @field:NotNull(message = "파일명은 필수입니다.")
        @field:NotBlank(message = "파일명은 필수입니다.")
        val pendingId: String,
        val status: Status
    ) {
        fun toCommand(user: UserEntity): PendingFileCommand.Update {
            if (pendingId.isBlank()) throw IllegalArgumentException("파일명은 필수입니다.")
            return PendingFileCommand.Update(
                user.email,
                pendingId = pendingId,
                status = status
            )
        }
    }
}