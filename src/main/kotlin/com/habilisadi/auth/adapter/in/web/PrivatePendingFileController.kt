package com.habilisadi.auth.adapter.`in`.web

import com.habilisadi.auth.application.dto.PendingFileRequest
import com.habilisadi.auth.application.dto.UserDetailCommand
import com.habilisadi.auth.application.port.`in`.SavePendingFileUseCase
import com.habilisadi.auth.application.port.`in`.UpdatePendingFileUseCase
import com.habilisadi.auth.application.port.`in`.UpdateUserProfileImageUseCase
import com.habilisadi.auth.domain.model.UserEntity
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/private/pending-files")
class PrivatePendingFileController(
    private val updateUserProfileImageUseCase: UpdateUserProfileImageUseCase,
    private val savePendingFileUseCase: SavePendingFileUseCase,
    private val updatePendingFileUseCase: UpdatePendingFileUseCase,
) {
    @PostMapping("")
    suspend fun uploadFilePending(
        @Valid @RequestBody req: PendingFileRequest.Save,
        @AuthenticationPrincipal principal: UserEntity
    ) = withContext(Dispatchers.IO) {

        val command = req.toCommand(principal)

        val res = savePendingFileUseCase.savePendingRequests(command)

        ResponseEntity.ok(res)
    }

    @PutMapping("complete")
    suspend fun complete(
        @Valid @RequestBody req: PendingFileRequest.Update,
        @AuthenticationPrincipal principal: UserEntity
    ) = withContext(Dispatchers.IO) {

        val pendingCommand = req.toCompleteCommand(principal)

        val pendingFileRes = updatePendingFileUseCase.update(pendingCommand)

        val userProfileCommand = UserDetailCommand.UpdateProfileImage.from(pendingFileRes)

        val res = updateUserProfileImageUseCase.updateUserProfileImage(userProfileCommand)

        ResponseEntity.ok(res)
    }
}