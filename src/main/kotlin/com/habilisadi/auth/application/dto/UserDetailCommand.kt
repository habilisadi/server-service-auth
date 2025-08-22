package com.habilisadi.auth.application.dto

import com.habilisadi.auth.domain.model.FileName
import com.habilisadi.auth.domain.model.FilePath
import com.habilisadi.auth.domain.model.ProfileImage
import com.habilisadi.auth.domain.model.UserDetailEntity

class UserDetailCommand {
    data class Update(
        val id: Long,
        val name: String,
        val phone: String,
        val userPk: String,
    ) {
        fun updateDomain(userDetailEntity: UserDetailEntity) {
            if (name.trim().isNotEmpty()) userDetailEntity.name = this.name
            if (phone.trim().isNotEmpty()) userDetailEntity.phone.update(this.phone)
        }
    }

    data class UpdateProfileImage(
        val id: String,
        val fileName: FileName,
        val filePath: FilePath,
    ) {

        companion object {
            fun from(pendingFileRes: PendingFileResponse.Update): UpdateProfileImage {
                if (pendingFileRes.fileName.value.isNullOrBlank()) throw IllegalArgumentException("fileName is null or blank")
                if (pendingFileRes.filePath.value.isNullOrBlank()) throw IllegalArgumentException("filePath is null or blank")
                if (pendingFileRes.userPk.isNullOrBlank()) throw IllegalArgumentException("userPk is null or blank")

                return UpdateProfileImage(
                    id = pendingFileRes.userPk,
                    fileName = pendingFileRes.fileName,
                    filePath = pendingFileRes.filePath,
                )
            }
        }


        fun updateDomain(userDetail: UserDetailEntity): ProfileImage {
            return userDetail.profileImage.update(filePath.value, fileName.value)
        }


    }

}