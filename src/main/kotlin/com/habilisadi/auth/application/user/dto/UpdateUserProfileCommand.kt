package com.habilisadi.auth.application.user.dto

import com.habilisadi.auth.domain.user.model.ProfileImage
import com.habilisadi.auth.domain.user.model.UserDetailEntity

data class UpdateUserProfileCommand(
    val id: Long,
    val filename: String,
) {
    fun updateDomain(userDetail: UserDetailEntity): ProfileImage {
        return userDetail.profileImage.update(filename)
    }
}