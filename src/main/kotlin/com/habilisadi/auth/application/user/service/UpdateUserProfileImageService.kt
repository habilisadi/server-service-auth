package com.habilisadi.auth.application.user.service

import com.habilisadi.auth.application.user.dto.UpdateUserProfileCommand
import com.habilisadi.auth.application.user.port.`in`.UpdateUserProfileImageUseCase
import com.habilisadi.auth.application.user.port.out.UserDetailRepository
import com.habilisadi.auth.common.dto.ResponseStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateUserProfileImageService(
    private val userDetailRepository: UserDetailRepository
) : UpdateUserProfileImageUseCase {

    override fun updateUserProfileImage(command: UpdateUserProfileCommand): ResponseStatus<String> {
        val userDetail = userDetailRepository.findById(command.id)
            .orElseThrow { IllegalArgumentException("User not found") }

        val basePath = "/files/user/$${userDetail.userEntity.id}/profile"
        val profileImage = userDetail.profileImage.update(basePath, command.filename)

        userDetailRepository.save(userDetail)

        return ResponseStatus.successData(
            profileImage.value,
            "User profile image updated successfully"
        )
    }
}