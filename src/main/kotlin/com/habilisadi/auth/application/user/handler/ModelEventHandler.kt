package com.habilisadi.auth.application.user.handler

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
@Transactional
annotation class ModelEventHandler
