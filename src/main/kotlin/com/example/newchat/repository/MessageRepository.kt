package com.example.newchat.repository

import com.example.newchat.entity.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long>