package me.kodac.prototype.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class SampleUser(username: String, password: String, roles: String) : User(
    username, password, toSetAuthorities(roles)) {

    companion object {
        fun toSetAuthorities(roles: String): Set<GrantedAuthority> {
            return roles.split(",").map(::SimpleGrantedAuthority).toSet()
        }
    }
}