package com.habilisadi.auth.domain.event

import java.time.Instant

data class UserDeActivatedEvent(
    val id: String,
    val timestamp: Instant = Instant.now()
)
