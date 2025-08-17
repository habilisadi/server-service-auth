package com.habilisadi.auth.infrastructure.config

import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(
    basePackages = ["com.habilisadi.auth.repository"],
)
class JpaConfig