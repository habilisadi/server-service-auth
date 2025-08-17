package com.habilisadi.auth.domain.user.converter

import com.habilisadi.auth.domain.user.model.UserRoleType
import jakarta.persistence.AttributeConverter

class UserRoleTypesConverter : AttributeConverter<MutableSet<UserRoleType>, String> {
    override fun convertToDatabaseColumn(attribute: MutableSet<UserRoleType>?): String {
        return attribute?.joinToString(",") { it.name } ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): MutableSet<UserRoleType> {
        return dbData?.split(",")?.map { UserRoleType.valueOf(it) }?.toMutableSet() ?: mutableSetOf()
    }
}