package com.habilisadi.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
class BeanConfig {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    @Bean
//    fun objectMapper() = ObjectMapper().apply {
//        registerModules(SecurityJackson2Modules.getModules(this::class.java.classLoader))
//        addMixIn(HashMap::class.java, UserEntity::class.java)
//        registerModule(OAuth2AuthorizationServerJackson2Module())
//    }
}