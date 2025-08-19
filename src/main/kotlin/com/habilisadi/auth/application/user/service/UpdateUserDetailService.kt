package com.habilisadi.auth.application.user.service

import com.habilisadi.auth.application.user.dto.UserDetailCommand
import com.habilisadi.auth.application.user.port.`in`.UpdateUserDetailUseCase
import com.habilisadi.auth.application.user.port.out.UserDetailRepository
import com.habilisadi.auth.common.dto.ResponseStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateUserDetailService(
    private val userDetailRepository: UserDetailRepository
) : UpdateUserDetailUseCase {

    override fun updateUserDetail(command: UserDetailCommand.Update): ResponseStatus<Boolean> {
        val userDetail = userDetailRepository.findById(command.id)
            .orElseThrow { IllegalArgumentException("User not found") }

        command.updateDomain(userDetail)

        userDetailRepository.save(userDetail)

        return ResponseStatus.success(true)
    }
}