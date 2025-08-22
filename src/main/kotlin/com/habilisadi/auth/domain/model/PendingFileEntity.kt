package com.habilisadi.auth.domain.model

import com.github.f4b6a3.ulid.UlidCreator
import com.habilisadi.file.Status
import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot
import java.time.Instant

@Entity
@Table(name = "pending_files")
data class PendingFileEntity(
    @Id
    var id: String? = null,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "file_name"))
    var fileName: FileName,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "file_path"))
    var filePath: FilePath,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING,

    var createdAt: Instant = Instant.now(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "expired_at"))
    var expiredAt: ExpiredAt
) : AbstractAggregateRoot<PendingFileEntity>() {

    @PrePersist
    fun init() {
        expiredAt = ExpiredAt.of(createdAt)
        if (id == null) id = UlidCreator.getUlid().toString()
    }
    
}