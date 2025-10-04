package com.saldi.ecom.user.infra

import com.saldi.ecom.user.domain.User
import com.saldi.ecom.user.domain.UserId
import com.saldi.ecom.user.domain.UserRepository
import org.springframework.stereotype.Repository

@Repository
class JpaUserRepository(
    private val jpaRepo: SpringDataUserRepository
) : UserRepository {

    override fun save(user: User): User {
        val entity = jpaRepo.save(user.toEntity())
        return entity.toDomain()
    }

    override fun findByUsername(username: String): User? =
        jpaRepo.findByUsername(username)?.toDomain()

    override fun findById(id: UserId): User? =
        jpaRepo.findById(id.value).orElse(null)?.toDomain()
}

// Mapper extension functions
fun User.toEntity() = UserEntity(id, username, email, passwordHash)
fun UserEntity.toDomain() = User(id, username, email, passwordHash)

// Spring Data JPA repository
interface SpringDataUserRepository : org.springframework.data.jpa.repository.JpaRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}