package com.example.newchat.controller

import com.example.newchat.dto.LoginRequest
import com.example.newchat.dto.RegisterRequest
import com.example.newchat.entity.User
import com.example.newchat.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {
    val encoder = BCryptPasswordEncoder()

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest, session: HttpSession): ResponseEntity<String> {
        val user: User = userService.getUserByName(request.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не найден")
        if (!encoder.matches(request.password, user.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный пароль")
        }

        val authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())
        SecurityContextHolder.getContext().authentication = authentication
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext())
        session.setAttribute("userId", user.id)

        return ResponseEntity.ok("Добро пожаловать, ${user.name}")
    }
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        if (userService.getUserByName(request.username) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Логин уже занят")
        }

        val hashPassword = encoder.encode(request.password)
        userService.createUser(request.name, request.age, request.username, hashPassword)
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован")
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<String> {
        session.invalidate()
        return ResponseEntity.ok("Вы успешно вышли")
    }

    @GetMapping("/register")
    fun registerPage(): String {
        return "register"
    }

    @GetMapping("/login")
    fun loginPage(): String {
        return "login"
    }
}