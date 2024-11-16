package com.example.newchat.controller
import com.example.newchat.entity.Chat
import com.example.newchat.dto.ChatRequest
import com.example.newchat.service.ChatService
import com.example.newchat.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (private val chatService: ChatService, private val userService: UserService){

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
}