package com.habilisadi.auth.adapter.`in`.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public/user-details")
class PublicUserDetailController {

    @GetMapping("test")
    suspend fun test() {
        ResponseEntity.ok("test ")
    }
}