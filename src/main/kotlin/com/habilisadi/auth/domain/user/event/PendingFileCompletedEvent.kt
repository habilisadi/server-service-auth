package com.habilisadi.auth.domain.user.event

import com.habilisadi.auth.domain.file.model.Status

data class PendingFileCompletedEvent(
    val id: String,
    val status: Status
)
