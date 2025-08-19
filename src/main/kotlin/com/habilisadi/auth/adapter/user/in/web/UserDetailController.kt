package com.habilisadi.auth.adapter.user.`in`.web

import com.habilisadi.auth.application.user.dto.UserDetailRequest
import com.habilisadi.auth.application.user.port.`in`.UpdateUserDetailUseCase
import com.habilisadi.auth.application.user.port.`in`.UpdateUserProfileImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user-details")
class UserDetailController(
    private val updateUserDetailUseCase: UpdateUserDetailUseCase,
    private val updateUserProfileImageUseCase: UpdateUserProfileImageUseCase,
) {
    @PutMapping("")
    suspend fun updateUserDetail(
        @RequestBody req: UserDetailRequest.Update
    ) = withContext(Dispatchers.IO) {
        val command = req.toCommand()

        val res = updateUserDetailUseCase.updateUserDetail(command)

        ResponseEntity.ok(res)
    }

}