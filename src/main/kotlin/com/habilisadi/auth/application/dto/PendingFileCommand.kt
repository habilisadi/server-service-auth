package com.habilisadi.auth.application.dto


import com.habilisadi.file.Destination
import com.habilisadi.file.SavePendingRequest
import com.habilisadi.file.Status
import com.habilisadi.file.UpdatePendingRequest

class PendingFileCommand {
    data class Save(
        val userPk: String,
        val fileName: String,
    ) {
        fun toGrpcRequest(): SavePendingRequest {
            return SavePendingRequest.newBuilder()
                .setFileName(fileName)
                .setFilePath("/profile")
                .setDestination(Destination.PROFILE)
                .setUserPk(userPk)
                .build()
        }
    }

    class Update(
        val id: String,
        val userPk: String,
        val status: Status
    ) {
        fun toGrpcRequest(): UpdatePendingRequest {
            return UpdatePendingRequest.newBuilder()
                .setId(id)
                .setUserPk(userPk)
                .setStatus(status)
                .setDestination(Destination.PROFILE)
                .build()
        }
    }
}