package com.saldi.ecom.user.application

import com.saldi.ecom.user.domain.User
import com.saldi.ecom.user.domain.UserId
import com.saldi.ecom.user.domain.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(username: String, email: String, rawPassword: String): User {
        if (userRepository.findByUsername(username) != null)
            throw IllegalArgumentException("Username already exists")

        val hash = passwordEncoder.encode(rawPassword)
        val user = User(UserId.generate(), username, email, hash)
        return userRepository.save(user)
    }

    fun login(username: String, rawPassword: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        return if (passwordEncoder.matches(rawPassword, user.passwordHash)) user else null
    }
}
