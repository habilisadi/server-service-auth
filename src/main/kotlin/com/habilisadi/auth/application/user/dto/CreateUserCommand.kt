package com.habilisadi.auth.application.user.dto

import com.habilisadi.auth.domain.user.model.Email
import com.habilisadi.auth.domain.user.model.Password
import com.habilisadi.auth.domain.user.model.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

data class CreateUserCommand(
    val email: String,
    val pwd: String,
) {
    companion object {
        fun create(email: String, pwd: String, confirmPwd: String): CreateUserCommand {
            if (pwd != confirmPwd) {
                throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
            }
            return CreateUserCommand(email, pwd)
        }
    }

    fun toDomain(passwordEncoder: PasswordEncoder) = UserEntity(
        email = Email(email),
        pwd = Password.createFromRawPassword(pwd, passwordEncoder)
    )
}