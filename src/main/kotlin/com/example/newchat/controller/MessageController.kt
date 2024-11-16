package com.example.newchat.controller

import com.example.newchat.service.MessageService
import com.example.newchat.service.UserService
import com.example.newchat.dto.MessageRequest
import com.example.newchat.service.ChatService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat/{chatId}")
class MessageController(
    private val chatService: ChatService,
    private val userService: UserService,
    private val messageService: MessageService
) {

    @PostMapping
    fun createMessage(
        @PathVariable chatId: Long,
        @RequestBody request: MessageRequest
    ): ResponseEntity<Long> {
        val createdID = messageService.createMessage(
            user = request.user,
            chat =  chatId,
            textMessage = request.textMessage
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(createdID)
    }

    @DeleteMapping("/{messageId}")
    fun deleteMessage(
        @PathVariable messageId: Long
    ): ResponseEntity<Void> {
        return if (messageService.deleteMessage(messageId)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}