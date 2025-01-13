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
                context.requireExplicitSave(false) // Автоматическое сохранение SecurityContext
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Создаём сессию только если требуется
            }
            .csrf { it.disable() } // Отключаем CSRF (если не используется)
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/auth/register", "/auth/login", "styles.css", "/css/**", "/js/**", "/images/**").permitAll() // Эти маршруты доступны всем
                    .anyRequest().authenticated() // Остальные требуют авторизации
            }
            .formLogin { it.disable() } // Отключаем встроенную форму логина
            .logout { logout ->
                logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login") // После выхода перенаправляем на страницу входа
            }
        return http.build()
    }
}