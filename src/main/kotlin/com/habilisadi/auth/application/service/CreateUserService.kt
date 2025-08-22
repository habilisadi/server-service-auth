package com.habilisadi.auth.application.service

import com.habilisadi.auth.application.dto.UserCommand
import com.habilisadi.auth.application.port.`in`.CreateUserUseCase
import com.habilisadi.auth.application.port.out.UserRepository
import com.habilisadi.auth.domain.model.PhoneNumber
import com.habilisadi.auth.domain.model.ProfileImage
import com.habilisadi.auth.domain.model.UserDetailEntity
import com.habilisadi.auth.shared.application.dto.ResponseStatus
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

        val userDetail = UserDetailEntity(
            name = "",
            phone = PhoneNumber("00000000000"),
            profileImage = ProfileImage("/"),
            userEntity = userDomain
        )

        userDomain.userDetailEntity = userDetail

        userRepository.save(userDomain)

        return ResponseStatus.success(true)
    }
}