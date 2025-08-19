package com.habilisadi.auth.application.file.service

import com.habilisadi.auth.application.file.dto.PendingFileCommand
import com.habilisadi.auth.application.file.dto.PendingFileResponse
import com.habilisadi.auth.application.file.port.`in`.UpdateStatusPendingFileUseCase
import com.habilisadi.auth.application.user.port.out.PendingFileRepository
import com.habilisadi.auth.domain.file.model.Status
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateStatusPendingFileService(
    private val pendingFileRepository: PendingFileRepository
) : UpdateStatusPendingFileUseCase {
    override fun updateStatus(command: PendingFileCommand.Update): PendingFileResponse.Update {
        val pendingFile = pendingFileRepository.findById(command.pendingId)
            .orElseThrow { IllegalArgumentException("Pending file not found") }

        when (command.status) {
            Status.COMPLETED -> pendingFile.complete()
            Status.FAILED -> pendingFile.fail()
            else -> throw IllegalArgumentException("Invalid status")
        }

        return PendingFileResponse.Update
            .from(pendingFile, command.email)
    }
}