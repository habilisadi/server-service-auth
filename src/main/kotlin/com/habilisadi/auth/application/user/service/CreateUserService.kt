package com.habilisadi.auth.application.user.service

import com.habilisadi.auth.application.user.dto.UserCommand
import com.habilisadi.auth.application.user.port.`in`.CreateUserUseCase
import com.habilisadi.auth.application.user.port.out.UserRepository
import com.habilisadi.auth.common.dto.ResponseStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateUserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : CreateUserUseCase {
    override fun createUser(command: UserCommand.Create): ResponseStatus<Boolean> {
        val userDomain = command.toDomain(passwordEncoder)

        val exists = userRepository.existsByEmail(userDomain.email)

        if (exists) {
            throw IllegalArgumentException("User already exists")
        }

        userRepository.save(userDomain)

        return ResponseStatus.success(true)
    }
}