package me.kodac.prototype.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TestJwtGenerator {

    companion object {
        private const val EXPIRES = 1800 * 1000
    }

    private val algorithm: Algorithm = Algorithm.HMAC256("KEY-DUMMY")

    fun generate(username: String): String {
        val issuedAt = Date()
        val notBefore = Date(issuedAt.time)
        val expiresAt = Date(issuedAt.time + EXPIRES.toLong())
        return JWT.create()
            .withIssuedAt(issuedAt)
            .withNotBefore(notBefore)
            .withExpiresAt(expiresAt)
            .withSubject(username)
            .sign(algorithm)
    }
}