package com.habilisadi.auth.domain.user.model

import com.github.f4b6a3.ulid.UlidCreator
import com.habilisadi.auth.domain.passkey.model.PasskeyCredential
import com.habilisadi.auth.domain.user.event.UserDeActivatedEvent
import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    var id: String? = null,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    var email: Email,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "pwd"))
    var pwd: Password,

    var isActive: Boolean = true,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "roles"))
    var roles: Role = Role(setOf(UserRoleType.ROLE_USER)),

    var createdAt: Instant = Instant.now(),

    var deletedAt: Instant? = null,

    @OneToOne(mappedBy = "userEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var userDetailEntity: UserDetailEntity? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var passkeyCredentials: MutableList<PasskeyCredential> = mutableListOf()
) : UserDetails, AbstractAggregateRoot<UserEntity>() {
    @PrePersist
    fun init() {
        if (id == null) id = UlidCreator.getUlid().toString()
    }

    fun addPasskeyCredential(passkeyCredential: PasskeyCredential) {
        passkeyCredentials.add(passkeyCredential)
    }

    fun removePasskeyCredential(passkeyCredential: PasskeyCredential) {
        passkeyCredentials.remove(passkeyCredential)
    }

    fun deActivate() {
        this.isActive = false
        this.deletedAt = Instant.now()
        registerEvent(UserDeActivatedEvent(id!!))
    }

    override fun getAuthorities() = this.roles.getAuthorities()

    override fun getPassword() = this.pwd.value

    override fun getUsername() = this.email.value

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = this.isActive
}