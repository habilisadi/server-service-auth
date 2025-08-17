package com.habilisadi.auth.domain.registerdClient.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Converter
class ClientAuthenticationMethodsConverter : AttributeConverter<Set<ClientAuthenticationMethod>, String> {

    override fun convertToDatabaseColumn(attribute: Set<ClientAuthenticationMethod>?): String {
        return attribute?.joinToString { it.value } ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): Set<ClientAuthenticationMethod> {
        return dbData?.split(",")?.map { ClientAuthenticationMethod(it) }?.toSet() ?: emptySet()
    }
}