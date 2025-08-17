package com.habilisadi.auth.domain.registerdClient.model

import com.habilisadi.auth.domain.registerdClient.converter.AuthorizationGrantTypesConverter
import com.habilisadi.auth.domain.registerdClient.converter.ClientAuthenticationMethodsConverter
import com.habilisadi.auth.domain.registerdClient.converter.ClientSettingsConverter
import com.habilisadi.auth.domain.registerdClient.converter.TokenSettingsConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import java.time.Instant

@Entity
@Table(name = "registered_client")
class RegisteredClientEntity(
    @Id
    val id: String,
    val clientId: String,
    val clientIdIssuedAt: Instant?,
    val clientSecret: String?,
    val clientSecretExpiresAt: Instant?,
    val clientName: String,

    @Convert(converter = ClientAuthenticationMethodsConverter::class)
    val clientAuthenticationMethods: Set<ClientAuthenticationMethod>,

    @Convert(converter = AuthorizationGrantTypesConverter::class)
    val authorizationGrantTypes: Set<AuthorizationGrantType>,

    val redirectUris: String?,
    val scopes: String,

    @Convert(converter = ClientSettingsConverter::class)
    val clientSettings: ClientSettings,

    @Convert(converter = TokenSettingsConverter::class)
    val tokenSettings: TokenSettings

) : AbstractAggregateRoot<RegisteredClientEntity>() {
    companion object {
        fun from(registeredClient: RegisteredClient): RegisteredClientEntity {
            return RegisteredClientEntity(
                registeredClient.id,
                registeredClient.clientId,
                registeredClient.clientIdIssuedAt,
                registeredClient.clientSecret,
                registeredClient.clientSecretExpiresAt,
                registeredClient.clientName,
                registeredClient.clientAuthenticationMethods,
                registeredClient.authorizationGrantTypes,
                registeredClient.redirectUris?.joinToString(","),
                registeredClient.scopes.joinToString(","),
                registeredClient.clientSettings,
                registeredClient.tokenSettings
            )
        }
    }

    fun toRegisteredClient(): RegisteredClient {
        return RegisteredClient.withId(id)
            .clientId(clientId)
            .clientIdIssuedAt(clientIdIssuedAt)
            .clientSecret(clientSecret)
            .clientSecretExpiresAt(clientSecretExpiresAt)
            .clientName(clientName)
            .clientAuthenticationMethods {
                clientAuthenticationMethods
            }
            .authorizationGrantTypes {
                authorizationGrantTypes
            }
            .redirectUris {
                redirectUris?.split(",")?.forEach { uri -> it.add(uri) }
            }
            .scopes {
                scopes.split(",").forEach { scope -> it.add(scope) }
            }
            .clientSettings(clientSettings)
            .tokenSettings(tokenSettings)
            .build()
    }
}