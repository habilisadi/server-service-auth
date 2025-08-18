package com.habilisadi.auth.application.user.dto

import com.habilisadi.auth.domain.user.model.UserDetailEntity

data class UpdateUserDetailCommand(
    val id: Long,
    val name: String,
    val phone: String,
) {
    fun updateDomain(userDetailEntity: UserDetailEntity) {
        userDetailEntity.name = this.name
    }
}
