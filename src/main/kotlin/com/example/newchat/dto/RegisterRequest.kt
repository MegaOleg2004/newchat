package com.example.newchat.dto

data class RegisterRequest(
    val name: String,
    val age: Int,
    val username: String,
    val password: String
)