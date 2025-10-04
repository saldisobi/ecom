package com.saldi.ecom.user.api.auth

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)