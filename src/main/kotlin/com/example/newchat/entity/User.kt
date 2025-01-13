package com.example.newchat.entity
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name", nullable = false)
    @field:NotBlank(message = "Имя пользователя не может быть пустым")
    var name: String = "",

    @Column(name = "age", nullable = false)
    var age: Int = 0,

    // authorization
    @Column(unique = true)

    @field:NotBlank(message = "Имя пользователя не может быть пустым")
    @field:Size(min = 5, message = "Логин должен быть не менее 5 символов")
    val username: String = "",

    @field:Size(min = 8, message = "Пароль должен быть не менее 8 символов")
    val password: String = "",
)
{
    constructor() : this(id = 0, name = "", age = 0, username = "", password = "")
}


