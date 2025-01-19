package com.example.newchat.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityContext { context ->
                context.requireExplicitSave(false)
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/auth/register", "/auth/login", "styles.css", "/css/**", "/js/**", "/images/**").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .logout { logout ->
                logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login")
            }
        return http.build()
    }
}