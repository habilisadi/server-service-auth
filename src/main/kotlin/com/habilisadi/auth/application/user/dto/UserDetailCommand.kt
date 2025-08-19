package com.habilisadi.auth.application.user.dto

import com.habilisadi.auth.domain.file.model.FileName
import com.habilisadi.auth.domain.file.model.FilePath
import com.habilisadi.auth.domain.user.model.Email
import com.habilisadi.auth.domain.user.model.ProfileImage
import com.habilisadi.auth.domain.user.model.UserDetailEntity

class UserDetailCommand {
    data class Update(
        val id: Long,
        val name: String,
        val phone: String,
    ) {
        fun updateDomain(userDetailEntity: UserDetailEntity) {
            if (name.trim().isNotEmpty()) userDetailEntity.name = this.name
            if (phone.trim().isNotEmpty()) userDetailEntity.phone.update(this.phone)
        }
    }


    data class UpdateProfileImage(
        val email: Email,
        val fileName: FileName,
        val filePath: FilePath,
    ) {
        fun updateDomain(userDetail: UserDetailEntity): ProfileImage {
            return userDetail.profileImage.update(filePath.value, fileName.value)
        }
    }

}