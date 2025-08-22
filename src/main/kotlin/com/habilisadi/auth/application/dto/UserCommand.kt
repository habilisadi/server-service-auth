package com.habilisadi.auth.application.dto

import com.habilisadi.auth.domain.model.Email
import com.habilisadi.auth.domain.model.Password
import com.habilisadi.auth.domain.model.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

class UserCommand {
    data class Create(
        val email: String,
        val pwd: String,
    ) {
        companion object {
            fun create(email: String, pwd: String, confirmPwd: String): Create {
                if (pwd.trim() != confirmPwd.trim()) {
                    throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
                }
                return Create(email.trim(), pwd.trim())
            }
        }

        fun toDomain(passwordEncoder: PasswordEncoder) = UserEntity(
            email = Email(email.trim()),
            pwd = Password.createFromRawPassword(pwd, passwordEncoder)
        )
    }
}