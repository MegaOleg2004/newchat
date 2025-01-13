package com.example.newchat.entity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "messages")
data class Message (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    val chat: Chat,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "textMessage", nullable = false)
    @field:NotBlank(message = "Сообщение не должно быть пустым")
    var textMessage: String = "",
)
{
    constructor(): this(id = 0, chat = Chat(), user = User(), textMessage = "")
}

