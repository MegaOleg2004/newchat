package com.example.newchat.controller

import com.example.newchat.service.UserService
import com.example.newchat.entity.User
import com.example.newchat.dto.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
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

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<Long> {
        val id = userService.createUser(request.name, request.age)
        return ResponseEntity.status(HttpStatus.CREATED).body(id)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UserRequest
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

