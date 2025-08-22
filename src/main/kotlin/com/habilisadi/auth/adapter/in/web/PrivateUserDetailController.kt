package com.habilisadi.auth.adapter.`in`.web

import com.habilisadi.auth.application.dto.UserDetailRequest
import com.habilisadi.auth.application.port.`in`.UpdateUserDetailUseCase
import com.habilisadi.auth.domain.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/private/user-details")
class PrivateUserDetailController(
    private val updateUserDetailUseCase: UpdateUserDetailUseCase
) {
    @PutMapping("")
    suspend fun updateUserDetail(
        @RequestBody req: UserDetailRequest.Update,
        @AuthenticationPrincipal principal: UserEntity,
    ) = withContext(Dispatchers.IO) {

        val command = req.toCommand(principal)

        val res = updateUserDetailUseCase.updateUserDetail(command)

        ResponseEntity.ok(res)
    }
}