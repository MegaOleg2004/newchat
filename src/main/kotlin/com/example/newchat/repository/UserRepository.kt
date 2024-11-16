package com.example.newchat.repository

import com.example.newchat.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>