package com.habilisadi.auth.application.service

import com.habilisadi.auth.application.port.out.UserRepository
import com.habilisadi.auth.domain.model.Email
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LoadUserPrincipalService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(Email(email))
            ?: throw RuntimeException("User not found with email: $email")
    }
}