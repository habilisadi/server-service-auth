package com.habilisadi.auth.application.user.service

import com.habilisadi.auth.application.user.dto.UserDetailCommand
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

    override fun updateUserProfileImage(command: UserDetailCommand.UpdateProfileImage): ResponseStatus<String> {
        val userDetail = userDetailRepository.findByUserEntityEmail(command.email)
            ?: throw IllegalArgumentException("User not found")

        val profileImage = userDetail.profileImage.update(command.filePath.value, command.fileName.value)

        userDetailRepository.save(userDetail)

        return ResponseStatus.successData(
            profileImage.value,
            "User profile image updated successfully"
        )
    }
}