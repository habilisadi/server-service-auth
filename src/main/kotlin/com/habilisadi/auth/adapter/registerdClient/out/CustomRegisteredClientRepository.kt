package com.habilisadi.auth.adapter.registerdClient.out

import com.habilisadi.auth.application.registerdClient.port.out.JpaRegisteredClientRepository
import com.habilisadi.auth.domain.registerdClient.model.RegisteredClientEntity
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