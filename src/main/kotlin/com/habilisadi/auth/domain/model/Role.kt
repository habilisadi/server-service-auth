package com.habilisadi.auth.domain.model

import com.habilisadi.auth.domain.converter.UserRoleTypesConverter
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable
import org.springframework.security.core.GrantedAuthority

@Embeddable
data class Role(
    @Convert(converter = UserRoleTypesConverter::class)
    private val value: Set<UserRoleType>
) {
    fun add(role: UserRoleType) {
        this.value.plus(role)
    }

    fun remove(role: UserRoleType) {
        this.value.minus(role)
    }

    fun getAuthorities(): Set<GrantedAuthority> {
        return value.map { GrantedAuthority { it.name } }.toSet()
    }
}