package com.habilisadi.auth.config

import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(
    basePackages = ["com.habilisadi.auth.repository"],
)
class JpaConfig