package me.kodac.prototype.api.config

import me.kodac.prototype.repository.UserMapper
import me.kodac.prototype.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.filter.GenericFilterBean

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val userMapper: UserMapper,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {

    companion object {
        private const val JWT_KEY = "KEY-DUMMY";  // dummy
    }

    override fun configure(web: WebSecurity) {
        web
            .debug(true)
            .ignoring()
            .antMatchers(
                "/images/**",
                "/js/**",
                "/css/**",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/templates/index.html"
            )
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .authorizeRequests()
                .mvcMatchers("/get-sample/*", "/post-sample")
                .permitAll()
            .mvcMatchers("/users/**")
                .hasRole("USER")
            .mvcMatchers("/manager/**")
                .hasRole("ADMIN")
            .anyRequest()
                .authenticated()
            .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            .and()
            .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            .and()
            .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
            .and()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        // @formatter:on
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.eraseCredentials(true)
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder)
    }

    fun tokenFilter(): GenericFilterBean {
        return JwtAuthenticationFilter(userMapper, JWT_KEY)
    }

    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _, response, _ ->
            if (response.isCommitted || response.getHeader("WWW-Authenticate") == "Bearer error=session_timeout") {
                return@AuthenticationEntryPoint
            }
            // TODO: handle
            response.setHeader("WWW-Authenticate", "Bearer realm=unauthorized")
            response.status = HttpStatus.UNAUTHORIZED.value()
        }
    }

    fun accessDeniedHandler(): AccessDeniedHandler {
        return AccessDeniedHandler { _, response, _ ->
            // TODO
            response.setHeader("WWW-Authenticate", "Bearer error=permission denied")
            response.status = HttpStatus.FORBIDDEN.value()
        }
    }

    fun authenticationSuccessHandler(): AuthenticationSuccessHandler {
        return JwtAuthenticationSuccessHandler(JWT_KEY)
    }

    fun authenticationFailureHandler(): AuthenticationFailureHandler {
        return AuthenticationFailureHandler { _, response, _ ->
            response.status = HttpStatus.UNAUTHORIZED.value()
        }
    }

    fun logoutSuccessHandler(): LogoutSuccessHandler {
        return HttpStatusReturningLogoutSuccessHandler()
    }

}