package com.habilisadi.auth.domain.model

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "passkey_credentials")
class PasskeyCredential(
    @Id
    var id: String? = null,

    @Column(columnDefinition = "BYTEA")
    var credentialId: ByteArray? = null,

    @Column(columnDefinition = "BYTEA")
    var publicKey: ByteArray? = null,

    var signatureCount: Long = 0,

    var createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_pk")
    var user: UserEntity
) {

    @PrePersist
    fun init() {
        if (id == null) id = UlidCreator.getUlid().toString()
        if (credentialId == null) credentialId = UlidCreator.getUlid().toBytes()
        if (publicKey == null) publicKey = UlidCreator.getUlid().toBytes()
    }

}
