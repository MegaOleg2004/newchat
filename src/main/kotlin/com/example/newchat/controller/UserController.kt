package com.example.newchat.controller

import com.example.newchat.service.UserService
import com.example.newchat.entity.User
import com.example.newchat.dto.UserRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {
    @GetMapping("/list")
    fun listAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/{username}")
    fun getUserByName(@Valid @RequestBody request: UserRequest): ResponseEntity<User> {
        val user = userService.getUserByName(request.username)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createUser(@Valid @RequestBody request: UserRequest): ResponseEntity<Long> {

        val id = userService.createUser(request.name, request.age, request.username,
            request.password)
        return ResponseEntity.status(HttpStatus.CREATED).body(id)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UserRequest
    ): ResponseEntity<Unit> {
        return if (userService.updateUser(id, request.name, request.age)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        return if (userService.deleteUser(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}

