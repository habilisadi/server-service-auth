package com.habilisadi.auth.service

import com.habilisadi.auth.dto.UserDto
import com.habilisadi.auth.entity.UserRoleType
import com.habilisadi.auth.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email)
            ?: throw RuntimeException("User not found with email: $email")
    }

    fun existsByEmail(email: String) =
        userRepository.existsByEmail(email)

    fun save(req: UserDto): Boolean {
        if (req.pwd != req.confirmPwd) {
            throw RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다")
        }

        if (existsByEmail(req.email)) {
            throw RuntimeException("이미 존재하는 이메일입니다")
        }

        if (req.pwd.length < 8) {
            throw RuntimeException("비밀번호는 최소 8자 이상이어야 합니다")
        }

        val userEntity = req.toEntity(passwordEncoder)

        userEntity.addRole(UserRoleType.ROLE_USER)

        userRepository.save(userEntity)

        return true
    }
}