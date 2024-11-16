package com.example.newchat.entity

import jakarta.persistence.*

@Entity
@Table(name = "chat", uniqueConstraints = [UniqueConstraint(columnNames = ["user1_id", "user2_id"])])
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    val user1: User,

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    val user2: User
){
    constructor() : this(id = 0, user1 = User(), user2 = User())
}

