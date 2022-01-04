package me.kodac.prototype.api

import me.kodac.prototype.domain.SampleUser
import me.kodac.prototype.repository.UserMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthSampleRestApi(private val userMapper: UserMapper) {

    @GetMapping("/users/me")
    fun get(@AuthenticationPrincipal sampleUser: SampleUser): ResponseEntity<Any> {

        val user = userMapper.findByUsername(sampleUser.username)
        val res = mapOf<String, Any?>(
            "username" to user?.username,
            "authorities" to user?.authorities,
            "enabled" to user?.isEnabled
        )
        return ResponseEntity.ok(res)
    }
}