package com.saldi.ecom.user.domain

import java.util.UUID

@JvmInline
value class UserId(val value: String) {
    init { require(value.isNotBlank()) { "UserId cannot be blank" } }

    companion object {
        fun generate(): UserId = UserId(UUID.randomUUID().toString())
    }
}

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val passwordHash: String
)