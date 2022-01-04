package me.kodac.prototype.api.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import me.kodac.prototype.domain.SampleUser
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.util.Date
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationSuccessHandler(jwtKey: String) : AuthenticationSuccessHandler {

    companion object {
        private const val EXPIRES = 1800 * 1000
    }

    private val algorithm: Algorithm = Algorithm.HMAC256(jwtKey)

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        if (response.isCommitted) {
            return
        }
        val jwt = generateToken(authentication)
        val tokenState = TokenState(jwt, EXPIRES)
        val mapper = ObjectMapper()
        response.apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = "UTF-8"
            status = HttpStatus.OK.value()
            writer.write(mapper.writeValueAsString(tokenState))
        }
        clearAuthenticationAttributes(request)
    }

    private fun generateToken(authentication: Authentication): String {
        val user = authentication.principal as SampleUser
        val issuedAt = Date()
        val notBefore = Date(issuedAt.time)
        val expiresAt = Date(issuedAt.time + EXPIRES.toLong())
        return JWT.create()
            .withIssuedAt(issuedAt)
            .withNotBefore(notBefore)
            .withExpiresAt(expiresAt)
            .withSubject(user.username)
            .sign(algorithm)
    }

    private fun clearAuthenticationAttributes(request: HttpServletRequest) {
        val session = request.getSession(false) ?: return
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
    }

    private data class TokenState(val token: String, val expires: Int)
}
