package com.habilisadi.auth.application.file.service

import com.habilisadi.auth.application.file.dto.PendingFileCommand
import com.habilisadi.auth.application.file.dto.PendingFileResponse
import com.habilisadi.auth.application.file.port.`in`.CreatePendingFileUseCase
import com.habilisadi.auth.application.user.port.out.PendingFileRepository
import com.habilisadi.auth.application.user.port.out.UserRepository
import com.habilisadi.auth.domain.file.model.ExpiredAt
import com.habilisadi.auth.domain.file.model.FileName
import com.habilisadi.auth.domain.file.model.FilePath
import com.habilisadi.auth.domain.file.model.PendingFileEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreatePendingFileService(
    private val pendingFileRepository: PendingFileRepository,
    private val userRepository: UserRepository
) : CreatePendingFileUseCase {

    override fun createPendingFile(command: PendingFileCommand.Create): PendingFileResponse.Create {
        val userEntity = userRepository.findByEmail(command.email)
            ?: throw IllegalArgumentException("User not found")

        val pendingFileEntity = PendingFileEntity(
            fileName = FileName(command.fileName),
            filePath = FilePath.of(userEntity.id!!, "/profiles"),
            expiredAt = ExpiredAt()
        )

        pendingFileRepository.save(pendingFileEntity)

        return PendingFileResponse.Create.from(pendingFileEntity)
    }
}