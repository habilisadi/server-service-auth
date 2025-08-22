package com.habilisadi.auth.application.port.out

import com.habilisadi.auth.domain.model.RegisteredClientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRegisteredClientRepository : JpaRepository<RegisteredClientEntity, String> {

    fun findAllByClientId(clientId: String): RegisteredClientEntity?

}