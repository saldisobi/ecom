package com.saldi.ecom.user.domain

interface UserRepository {
    fun save(user: User): User
    fun findByUsername(username: String): User?
    fun findById(id: UserId): User?
}