package com.example.newchat.controller

import com.example.newchat.entity.Chat
import com.example.newchat.dto.ChatRequest
import com.example.newchat.service.ChatService
import com.example.newchat.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chats")
class ChatController (private val chatService: ChatService, private val userService: UserService){

    @GetMapping("/currentUser")
    fun getCurrentUser(session: HttpSession): ResponseEntity<Long?> {
        val userId = session.getAttribute("userId") as? Long
        return if (userId != null) {
            ResponseEntity.ok(userId)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping
    fun createChat(@RequestBody request: ChatRequest): ResponseEntity<Long>{
        val id = chatService.createChat(request.user1, request.user2)
        return ResponseEntity.status(HttpStatus.CREATED).body(id)
    }

    @DeleteMapping("/{id}")
    fun deleteChat(@PathVariable id: Long): ResponseEntity<Long>{
        return if (chatService.deleteChat(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/list")
    fun listAllChats(): List<Chat> {
        return chatService.getAllChats()
    }

    @GetMapping("/list/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Chat> {
        val chat = chatService.getChatById(id)
        return if (chat != null) {
            ResponseEntity.ok(chat)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/overview")
    fun getChatsOverview(session: HttpSession): ResponseEntity<Map<String, Any>> {
        val userId = session.getAttribute("userId") as? Long
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val userChats = chatService.getAllChats()
            .filter { it.user1.id == userId || it.user2.id == userId }

        val chatUserIds = userChats.flatMap { listOf(it.user1.id, it.user2.id) }
            .filter { it != userId }

        val availableUsers = userService.getAllUsers()
            .filter { it.id != userId && it.id !in chatUserIds }

        return ResponseEntity.ok(
            mapOf(
                "chats" to userChats,
                "users" to availableUsers
            )
        )
    }
}