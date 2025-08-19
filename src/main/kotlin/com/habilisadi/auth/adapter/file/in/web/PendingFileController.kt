package com.habilisadi.auth.adapter.file.`in`.web

import com.habilisadi.auth.application.file.dto.PendingFileRequest
import com.habilisadi.auth.application.file.port.`in`.CreatePendingFileUseCase
import com.habilisadi.auth.application.file.port.`in`.UpdateStatusPendingFileUseCase
import com.habilisadi.auth.application.user.dto.UserDetailCommand
import com.habilisadi.auth.application.user.port.`in`.UpdateUserProfileImageUseCase
import com.habilisadi.auth.domain.user.model.UserEntity
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/pending-files")
class PendingFileController(
    private val createPendingFileUseCase: CreatePendingFileUseCase,
    private val updateStatusPendingFileUseCase: UpdateStatusPendingFileUseCase,
    private val updateUserProfileImageUseCase: UpdateUserProfileImageUseCase
) {
    @PostMapping("")
    suspend fun uploadFile(
        @Valid @RequestBody req: PendingFileRequest.Create,
        @AuthenticationPrincipal principal: UserEntity
    ) = withContext(Dispatchers.IO) {

        val command = req.toCommand(principal)

        val res = createPendingFileUseCase.createPendingFile(command)

        ResponseEntity.ok(res)
    }

    @PutMapping("complete")
    suspend fun complete(
        @Valid @RequestBody req: PendingFileRequest.Update,
        @AuthenticationPrincipal principal: UserEntity
    ) = withContext(Dispatchers.IO) {

        val pendingFileCommand = req.toCommand(principal)

        val file = updateStatusPendingFileUseCase.updateStatus(pendingFileCommand)

        val userProfileCommand = UserDetailCommand.UpdateProfileImage(
            email = file.email,
            fileName = file.fileName,
            filePath = file.filePath
        )

        val res = updateUserProfileImageUseCase.updateUserProfileImage(userProfileCommand)

        ResponseEntity.ok(res)
    }
}