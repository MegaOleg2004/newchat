package com.example.newchat.dto

data class MessageRequest(
    val chat: Long,
    val user: Long,
    var textMessage: String
)