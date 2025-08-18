package com.habilisadi.auth.application.user.handler

import com.habilisadi.auth.application.user.port.out.UserRepository
import com.habilisadi.auth.domain.user.event.UserDeActivatedEvent
import org.springframework.context.event.EventListener

@ModelEventHandler
class UserEventHandler(
    private val userRepository: UserRepository
) {
    @EventListener
    fun deactivateUser(event: UserDeActivatedEvent) {
        userRepository.findById(event.id).ifPresent {
            it.isActive = false
            it.deletedAt = event.timestamp
            userRepository.save(it)
        }
    }
}