package me.kodac.prototype.service

import me.kodac.prototype.repository.UserMapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userMapper: UserMapper) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userMapper.findByUsername(username)
            ?: throw UsernameNotFoundException("user not found. username = [$username]")
    }
}