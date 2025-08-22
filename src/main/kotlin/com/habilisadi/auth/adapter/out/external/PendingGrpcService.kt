package com.habilisadi.auth.adapter.out.external

import com.habilisadi.auth.application.dto.PendingFileCommand
import com.habilisadi.auth.application.dto.PendingFileResponse
import com.habilisadi.auth.application.port.`in`.SavePendingFileUseCase
import com.habilisadi.auth.application.port.`in`.UpdatePendingFileUseCase
import com.habilisadi.file.PendingServiceGrpc
import org.springframework.stereotype.Service

@Service
class PendingGrpcService(
    private val blockingStub: PendingServiceGrpc.PendingServiceBlockingStub
) : SavePendingFileUseCase, UpdatePendingFileUseCase {

    override fun savePendingRequests(command: PendingFileCommand.Save): PendingFileResponse.Save {

        val savePendingReq = command.toGrpcRequest()

        val savePendingRes = blockingStub.savePendingRequests(savePendingReq)

        return PendingFileResponse.Save.from(savePendingRes)
    }

    override fun update(command: PendingFileCommand.Update): PendingFileResponse.Update {
        val updatePendingReq = command.toGrpcRequest()

        val updatePendingRes = blockingStub.updatePendingRequests(updatePendingReq)

        return PendingFileResponse.Update.from(updatePendingRes)
    }
}
