package com.habilisadi.auth.domain.registerdClient.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings

@Converter
class ClientSettingsConverter : AttributeConverter<ClientSettings, String> {
    override fun convertToDatabaseColumn(attribute: ClientSettings?): String {
        return attribute?.settings?.toString() ?: "{}"
    }

    override fun convertToEntityAttribute(dbData: String?): ClientSettings {
        return try {
            val mapper = jacksonObjectMapper()
            val settingsMap: Map<String, Any> = mapper.readValue(dbData ?: "{}")

            val builder = ClientSettings.builder()

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
            ClientSettings.builder().build()
        }
    }
}