package com.example.newchat

import com.example.newchat.entity.Chat
import com.example.newchat.entity.Message
import com.example.newchat.entity.User
import com.example.newchat.repository.ChatRepository
import com.example.newchat.repository.MessageRepository
import com.example.newchat.repository.UserRepository
import com.example.newchat.service.MessageService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class MessageServiceTest {

    @Mock
    private lateinit var chatRepository: ChatRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var messageRepository: MessageRepository

    @InjectMocks
    private lateinit var messageService: MessageService

    @Test
    fun `test createMessage with valid data`() {
        val userId = 1L
        val chatId = 1L
        val textMessage = "Hello, world!"
        val mockUser1 = User(id = 1L)
        val mockUser2 = User(id = 2L)

        val mockChat = Chat(
            id = chatId,
            user1 = mockUser1,
            user2 = mockUser2
        )
        val mockMessage = Message(chat = mockChat, user = mockUser1, textMessage = textMessage)
        val messageId = mockMessage.id

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(mockUser1))
        `when`(chatRepository.findById(chatId)).thenReturn(Optional.of(mockChat))
        `when`(messageRepository.save(mockMessage)).thenReturn(mockMessage)

        val result = messageService.createMessage(userId, chatId, textMessage)

        assertEquals(messageId, result) // Проверка, что возвращается корректный ID сообщения

        verify(userRepository).findById(userId)
        verify(chatRepository).findById(chatId)
        verify(messageRepository).save(mockMessage)
    }

    @Test
    fun `test createMessage with missing user`() {
        // Given
        val userId = 1L
        val chatId = 1L
        val textMessage = "Hello, world!"

        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            messageService.createMessage(userId, chatId, textMessage)
        }
        assertEquals("User not found", exception.message)

        verify(userRepository).findById(userId)
        verifyNoInteractions(chatRepository, messageRepository)
    }
}