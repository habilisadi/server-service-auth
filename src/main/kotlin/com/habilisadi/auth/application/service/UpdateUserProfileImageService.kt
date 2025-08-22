package com.habilisadi.auth.application.service

import com.habilisadi.auth.application.dto.UserDetailCommand
import com.habilisadi.auth.application.port.`in`.UpdateUserProfileImageUseCase
import com.habilisadi.auth.application.port.out.UserDetailRepository
import com.habilisadi.auth.shared.application.dto.ResponseStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateUserProfileImageService(
    private val userDetailRepository: UserDetailRepository
) : UpdateUserProfileImageUseCase {

    override fun updateUserProfileImage(command: UserDetailCommand.UpdateProfileImage): ResponseStatus<String> {
        val userDetail = userDetailRepository.findByUserEntityId(command.id)
            ?: throw IllegalArgumentException("User not found")

        val profileImage = userDetail.profileImage.update(command.filePath.value, command.fileName.value)

        userDetail.profileImage = profileImage

        userDetailRepository.save(userDetail)


        return ResponseStatus.successData(
            profileImage.value,
            "User profile image updated successfully"
        )
    }
}