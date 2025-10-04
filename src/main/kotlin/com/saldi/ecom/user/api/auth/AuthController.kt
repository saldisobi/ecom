package com.saldi.ecom.user.api.auth

import com.saldi.ecom.user.application.UserService
import com.saldi.ecom.user.infra.JwtTokenProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): AuthResponse {
        val user = userService.register(request.username, request.email, request.password)
        val token = jwtTokenProvider.generateToken(user.id)
        return AuthResponse(token)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthResponse {
        val user = userService.login(request.username, request.password)
            ?: throw IllegalArgumentException("Invalid credentials")
        val token = jwtTokenProvider.generateToken(user.id)
        return AuthResponse(token)
    }
}