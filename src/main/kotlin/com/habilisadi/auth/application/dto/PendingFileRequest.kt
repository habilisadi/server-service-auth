package com.habilisadi.auth.application.dto

import com.habilisadi.auth.domain.model.UserEntity
import com.habilisadi.file.Status
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


class PendingFileRequest {

    data class Save(
        @field:NotNull(message = "파일명은 필수입니다.")
        @field:NotBlank(message = "파일명은 필수입니다.")
        val fileName: String,
    ) {

        fun toCommand(): PendingFileCommand.Save {
            if (fileName.isBlank()) throw IllegalArgumentException("파일명은 필수입니다.")

            return PendingFileCommand.Save(
                "01K38WS7210XWXC52YDT9VE6AF",
                fileName = fileName,
            )
        }

        fun toCommand(user: UserEntity): PendingFileCommand.Save {
            if (fileName.isBlank()) throw IllegalArgumentException("파일명은 필수입니다.")

            return PendingFileCommand.Save(
                user.id!!,
                fileName = fileName,
            )
        }
    }

    data class Update(
        @field:NotNull(message = "파일명은 필수입니다.")
        @field:NotBlank(message = "파일명은 필수입니다.")
        val pendingId: String
    ) {
        fun toCompleteCommandTest(): PendingFileCommand.Update {
            return PendingFileCommand.Update(
                id = "01K38Y3G59H39W443FJPM5BK9R",
                userPk = "01K38WS7210XWXC52YDT9VE6AF",
                status = Status.COMPLETED,
            )
        }

        fun toCompleteCommand(user: UserEntity) = toCommand(user, Status.COMPLETED)
        fun toFailCommand(user: UserEntity) = toCommand(user, Status.FAILED)

        fun toCommand(user: UserEntity, status: Status): PendingFileCommand.Update {
            if (pendingId.isBlank()) throw IllegalArgumentException("파일명은 필수입니다.")
            return PendingFileCommand.Update(
                id = pendingId,
                userPk = user.id!!,
                status = status,
            )
        }

    }
}