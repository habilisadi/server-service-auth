package com.habilisadi.auth.adapter.out.persistence

import com.habilisadi.auth.application.port.out.JpaRegisteredClientRepository
import com.habilisadi.auth.domain.model.RegisteredClientEntity
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Component

@Component
class CustomRegisteredClientRepository(
    private val repository: JpaRegisteredClientRepository
) : RegisteredClientRepository {
    override fun save(registeredClient: RegisteredClient) {
        repository.save(RegisteredClientEntity.from(registeredClient))
    }

    override fun findById(id: String): RegisteredClient? {
        val entity = repository.findById(id)
            .orElse(null) ?: return null

        return entity.toRegisteredClient()
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        val entity = repository.findAllByClientId(clientId)
            ?: return null

        return entity.toRegisteredClient()
    }
}