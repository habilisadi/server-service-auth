package com.habilisadi.auth.application.user.handler

import com.habilisadi.auth.application.user.port.out.PendingFileRepository
import com.habilisadi.auth.domain.file.model.Status
import com.habilisadi.auth.domain.user.event.PendingFileCompletedEvent
import org.springframework.context.event.EventListener


@ModelEventHandler
class PendingFileEventHandler(
    private val pendingFileRepository: PendingFileRepository
) {

    @EventListener
    fun updateStatus(event: PendingFileCompletedEvent) {
        pendingFileRepository.findByIdAndStatus(event.id, Status.PENDING)?.apply {
            this.status = event.status
            pendingFileRepository.save(this)
        }
    }
}