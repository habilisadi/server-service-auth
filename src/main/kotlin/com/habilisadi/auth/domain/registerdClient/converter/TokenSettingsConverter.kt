package com.habilisadi.auth.domain.registerdClient.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings

@Converter
class TokenSettingsConverter : AttributeConverter<TokenSettings, String> {
    override fun convertToDatabaseColumn(attribute: TokenSettings?): String {
        return attribute?.settings?.toString() ?: "{}"
    }

    override fun convertToEntityAttribute(dbData: String?): TokenSettings {
        return try {
            val mapper = jacksonObjectMapper()
            val settingsMap: Map<String, Any> = mapper.readValue(dbData ?: "{}")

            val builder = TokenSettings.builder()
            
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