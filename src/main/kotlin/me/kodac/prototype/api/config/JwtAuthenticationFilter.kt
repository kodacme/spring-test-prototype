package me.kodac.prototype.api.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import me.kodac.prototype.repository.UserMapper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val userMapper: UserMapper,
    key: String
) : GenericFilterBean() {

    private val algorithm = Algorithm.HMAC256(key)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val token = resolveToken(request)
        if (token == null) {
            chain?.doFilter(request, response)
            return
        }
        try {
            authentication(verifyToken(token))
        } catch (e: Exception) {
            if (response is HttpServletResponse) {
                response.status = HttpStatus.UNAUTHORIZED.value()
                response.writer.write("{\"result\":\"error\"}")
            }
            SecurityContextHolder.clearContext()
        }
        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: ServletRequest?): String? {
        if (request == null) {
            return null
        }
        val req = request as HttpServletRequest
        val token = req.getHeader("Authorization")
        if (token == null || !token.startsWith("Bearer ")) {
            return null
        }
        return token.substring(7)
    }

    private fun verifyToken(token: String): DecodedJWT {
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    private fun authentication(jwt: DecodedJWT) {
        val username = jwt.subject
        userMapper.findByUsername(username)
            ?.let {
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(
                        it,
                        null,
                        it.authorities
                    )
            }
    }
}