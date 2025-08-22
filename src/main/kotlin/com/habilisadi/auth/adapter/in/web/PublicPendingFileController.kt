package com.habilisadi.auth.adapter.`in`.web

import com.habilisadi.auth.application.dto.PendingFileRequest
import com.habilisadi.auth.application.dto.UserDetailCommand
import com.habilisadi.auth.application.port.`in`.SavePendingFileUseCase
import com.habilisadi.auth.application.port.`in`.UpdatePendingFileUseCase
import com.habilisadi.auth.application.port.`in`.UpdateUserProfileImageUseCase
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


// test 용도
@RestController
@RequestMapping("/api/v1/public/pending-files")
class PublicPendingFileController(
    private val updateUserProfileImageUseCase: UpdateUserProfileImageUseCase,
    private val savePendingFileUseCase: SavePendingFileUseCase,
    private val updatePendingFileUseCase: UpdatePendingFileUseCase,
) {

    @PostMapping("")
    suspend fun uploadFilePending(
        @Valid @RequestBody req: PendingFileRequest.Save,
    ) = withContext(Dispatchers.IO) {

        val command = req.toCommand()

        val res = savePendingFileUseCase.savePendingRequests(command)

        ResponseEntity.ok(res)
    }

    @PutMapping("complete")
    suspend fun complete(
        @Valid @RequestBody req: PendingFileRequest.Update,
    ) = withContext(Dispatchers.IO) {

        val pendingCommand = req.toCompleteCommandTest()

        val pendingFileRes = updatePendingFileUseCase.update(pendingCommand)

        val userProfileCommand = UserDetailCommand.UpdateProfileImage.from(pendingFileRes)

        val res = updateUserProfileImageUseCase.updateUserProfileImage(userProfileCommand)

        ResponseEntity.ok(res)
    }
}