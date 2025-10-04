package com.saldi.ecom.user.infra

import com.saldi.ecom.user.domain.UserId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider {

    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(userId: UserId): String = Jwts.builder().setSubject(userId.value).setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + 3600_000)).signWith(key).compact()

    fun validateToken(token: String): Boolean = try {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        true
    } catch (e: Exception) {
        false
    }

    fun getUserId(token: String): UserId =
        UserId(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject)
}
