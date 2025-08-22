package com.habilisadi.auth.domain.model

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*

@Entity
@Table(name = "users_detail")
data class UserDetailEntity(
    @Id
    var id: String? = null,

    var name: String = "",

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "phone"))
    var phone: PhoneNumber,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "profile_image"))
    var profileImage: ProfileImage,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_pk")
    var userEntity: UserEntity
) {
    @PrePersist
    fun init() {
        if (id == null) id = UlidCreator.getUlid().toString()
    }
}
