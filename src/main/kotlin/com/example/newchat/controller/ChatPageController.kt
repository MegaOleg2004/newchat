package com.example.newchat.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ChatPageController {
    @GetMapping("/chats")
    fun chatsPage(): String {
        return "chats"
    }
    @GetMapping("/chat.html")
    fun chatPage(@RequestParam("chatId") chatId: Long?, model: Model): String {
        model.addAttribute("chatId", chatId)
        return "chat"
    }
}