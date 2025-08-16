package com.habilisadi.auth.repository

import com.habilisadi.auth.entity.RegisteredClientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRegisteredClientRepository : JpaRepository<RegisteredClientEntity, String> {
    fun findAllByClientId(clientId: String): RegisteredClientEntity?
}