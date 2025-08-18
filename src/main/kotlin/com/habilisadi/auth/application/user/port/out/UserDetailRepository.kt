package com.habilisadi.auth.application.user.port.out

import com.habilisadi.auth.domain.user.model.UserDetailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailRepository : JpaRepository<UserDetailEntity, Long>