package com.habilisadi.auth.application.port.out

import com.habilisadi.auth.domain.model.Email
import com.habilisadi.auth.domain.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: Email): UserEntity?
    fun existsByEmail(email: Email): Boolean
}