package com.habilisadi.auth.application.port.out

import com.habilisadi.auth.domain.model.UserDetailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailRepository : JpaRepository<UserDetailEntity, Long> {
    //    fun findByUserEntityEmail(email: Email): UserDetailEntity?
    fun findByUserEntityId(id: String): UserDetailEntity?
}