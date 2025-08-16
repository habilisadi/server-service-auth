package com.habilisadi.auth.entity

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    var id: String? = null,
    var email: String,
    var pwd: String,
    var isActive: Boolean = true,
    var roles: String = "",
    var createdAt: Instant = Instant.now(),

    @OneToOne(mappedBy = "userEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var userDetailEntity: UserDetailEntity? = null,
) : UserDetails {
    @PrePersist
    fun init() {
        if (id == null) id = UlidCreator.getUlid().toString()
    }

    fun addRole(role: UserRoleType) {
        if (this.roles.isEmpty()) {
            this.roles = role.name
        } else {
            this.roles = roles.split(",")
                .toSet()
                .plus(role.name)
                .joinToString(",")
        }
    }

    fun removeRole(role: UserRoleType) {
        this.roles = roles.split(",")
            .toSet()
            .minus(role.name)
            .joinToString(",")
    }

    override fun getAuthorities() =
        this.roles.split(",")
            .map {
                GrantedAuthority { it }
            }
            .toMutableList()

    override fun getPassword() = this.pwd


    override fun getUsername() = this.email
    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = this.isActive
}