package com.example.newchat.entity
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String = "",
    var age: Int = 0
)
{
    constructor() : this(id = 0, name = "", age = 0)
}


