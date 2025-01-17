package com.example.newchat.controller

import com.example.newchat.service.MessageService
import com.example.newchat.dto.MessageRequest
import com.example.newchat.entity.Message
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/chat/{chatId}")
class MessageController(
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

    @GetMapping("/messages/{messageId}")
    fun getMessageById(
        @PathVariable chatId: Long,
        @PathVariable messageId: Long
    ): ResponseEntity<Message> {
        val message = messageService.getMessageByIdAndChatId(messageId, chatId)
        return if (message != null) {
            ResponseEntity.ok(message)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/messages")
    fun getMessages(@PathVariable chatId: Long): ResponseEntity<List<Message>> {
        val messages = messageService.getMessagesByChatId(chatId)
        return ResponseEntity.ok(messages)
    }
}