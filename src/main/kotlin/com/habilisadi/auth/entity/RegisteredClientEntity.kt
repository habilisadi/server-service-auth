package com.habilisadi.auth.entity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
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
    val clientAuthenticationMethods: String,
    val authorizationGrantTypes: String,
    val redirectUris: String?,
    val scopes: String,
    val clientSettings: String,
    val tokenSettings: String
) {
    companion object {
        fun from(registeredClient: RegisteredClient): RegisteredClientEntity {
            return RegisteredClientEntity(
                registeredClient.id,
                registeredClient.clientId,
                registeredClient.clientIdIssuedAt,
                registeredClient.clientSecret,
                registeredClient.clientSecretExpiresAt,
                registeredClient.clientName,
                registeredClient.clientAuthenticationMethods.map { it.value }.joinToString(","),
                registeredClient.authorizationGrantTypes.map { it.value }.joinToString(","),
                registeredClient.redirectUris?.joinToString(","),
                registeredClient.scopes.joinToString(","),
                registeredClient.clientSettings.settings.toString(),
                registeredClient.tokenSettings.settings.toString()
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
                clientAuthenticationMethods.split(",").forEach { method ->
                    it.add(ClientAuthenticationMethod(method))
                }
            }
            .authorizationGrantTypes {
                authorizationGrantTypes.split(",")
                    .forEach { grantType -> it.add(AuthorizationGrantType(grantType)) }
            }
            .redirectUris {
                redirectUris?.split(",")?.forEach { uri -> it.add(uri) }
            }
            .scopes {
                scopes.split(",").forEach { scope -> it.add(scope) }
            }
            .clientSettings(parseClientSettings())
            .tokenSettings(parseTokenSettings())
            .build()
    }

    private fun parseClientSettings(): ClientSettings {
        return try {
            val mapper = jacksonObjectMapper()
            val settingsMap: Map<String, Any> = mapper.readValue(clientSettings)

            val builder = ClientSettings.builder()

            // Apply common client settings
            settingsMap["requireAuthorizationConsent"]?.let {
                if (it is Boolean) builder.requireAuthorizationConsent(it)
            }
            settingsMap["requireProofKey"]?.let {
                if (it is Boolean) builder.requireProofKey(it)
            }
            settingsMap["jwkSetUrl"]?.let {
                if (it is String) builder.jwkSetUrl(it)
            }

            builder.build()
        } catch (e: Exception) {
            // Fallback to default settings if parsing fails
            ClientSettings.builder().build()
        }
    }

    private fun parseTokenSettings(): TokenSettings {
        return try {
            val mapper = jacksonObjectMapper()
            val settingsMap: Map<String, Any> = mapper.readValue(tokenSettings)

            val builder = TokenSettings.builder()

            // Apply common token settings
            settingsMap["accessTokenTimeToLive"]?.let {
                if (it is String) builder.accessTokenTimeToLive(java.time.Duration.parse(it))
            }
            settingsMap["refreshTokenTimeToLive"]?.let {
                if (it is String) builder.refreshTokenTimeToLive(java.time.Duration.parse(it))
            }
            settingsMap["reuseRefreshTokens"]?.let {
                if (it is Boolean) builder.reuseRefreshTokens(it)
            }

            builder.build()
        } catch (e: Exception) {
            // Fallback to default settings if parsing fails
            TokenSettings.builder().build()
        }
    }
}