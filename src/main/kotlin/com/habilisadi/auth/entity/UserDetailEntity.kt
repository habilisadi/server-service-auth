package com.habilisadi.auth.entity

import jakarta.persistence.*

@Entity
@Table(name = "users_detail")
data class UserDetailEntity(
    @Id
    var id: Long? = null,
    var name: String = "",
    var phone: String = "",
    var profileImage: String = "",

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_pk")
    var userEntity: UserEntity
)
