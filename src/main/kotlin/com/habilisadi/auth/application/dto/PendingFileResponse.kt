package com.habilisadi.auth.application.dto


import com.habilisadi.auth.domain.model.FileName
import com.habilisadi.auth.domain.model.FilePath
import com.habilisadi.auth.domain.model.PendingFileEntity
import com.habilisadi.file.SavePendingResponse
import com.habilisadi.file.UpdatePendingResponse

class PendingFileResponse {

    data class Save(
        val pendingId: String,
        val fileName: String,
        val filePath: String,
    ) {
        companion object {
            fun from(pendingFileEntity: PendingFileEntity): Save {
                if (pendingFileEntity.id == null) {
                    throw IllegalArgumentException("PendingFileEntity id is null")
                }

                return Save(
                    pendingId = pendingFileEntity.id!!,
                    fileName = pendingFileEntity.fileName.value,
                    filePath = pendingFileEntity.filePath.value,
                )
            }

            fun from(grpcResponse: SavePendingResponse) =
                Save(
                    pendingId = grpcResponse.id,
                    fileName = grpcResponse.nextFileName,
                    filePath = grpcResponse.nextFilePath,
                )

        }
    }

    data class Update(
        val userPk: String,
        val filePath: FilePath,
        val fileName: FileName,
    ) {
        companion object {
            fun from(grpcResponse: UpdatePendingResponse): Update {
                return Update(
                    userPk = grpcResponse.userPk,
                    filePath = FilePath(grpcResponse.nextFilePath),
                    fileName = FileName(grpcResponse.nextFileName)
                )
            }
        }
    }
}