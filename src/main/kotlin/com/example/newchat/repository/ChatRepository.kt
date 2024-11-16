package com.example.newchat.repository

import com.example.newchat.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, Long>