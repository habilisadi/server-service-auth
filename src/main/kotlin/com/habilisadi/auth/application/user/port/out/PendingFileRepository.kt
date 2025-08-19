package com.habilisadi.auth.application.user.port.out

import com.habilisadi.auth.domain.file.model.PendingFileEntity
import com.habilisadi.auth.domain.file.model.Status
import org.springframework.data.jpa.repository.JpaRepository

interface PendingFileRepository : JpaRepository<PendingFileEntity, String> {
    fun findByIdAndStatus(id: String, status: Status): PendingFileEntity?
}