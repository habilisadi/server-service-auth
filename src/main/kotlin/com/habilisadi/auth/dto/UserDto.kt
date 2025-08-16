package com.habilisadi.auth.dto

import com.habilisadi.auth.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

class UserDto(
    val email: String,
    val pwd: String,
    val confirmPwd: String
) {
    fun toEntity(passwordEncoder: PasswordEncoder) = UserEntity(
        email = email,
        pwd = passwordEncoder.encode(pwd),
    )
}