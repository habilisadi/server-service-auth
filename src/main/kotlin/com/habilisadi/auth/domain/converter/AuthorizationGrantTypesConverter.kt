package com.habilisadi.auth.domain.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.security.oauth2.core.AuthorizationGrantType

@Converter
class AuthorizationGrantTypesConverter : AttributeConverter<Set<AuthorizationGrantType>, String> {
    override fun convertToDatabaseColumn(attribute: Set<AuthorizationGrantType>?): String {
        return attribute?.joinToString { it.value } ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): Set<AuthorizationGrantType> {
        return dbData?.split(",")?.map { AuthorizationGrantType(it) }?.toSet() ?: emptySet()
    }
}