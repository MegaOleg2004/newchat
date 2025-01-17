package com.example.newchat.service
import com.example.newchat.entity.Chat
import com.example.newchat.repository.ChatRepository
import com.example.newchat.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ChatService (private val chatRepository: ChatRepository, private val userRepository: UserRepository) {
    fun createChat(user1Id: Long, user2Id: Long): Long {
        val user1 = userRepository.findById(user1Id).orElseThrow { RuntimeException("Пользователь не найден") }
        val user2 = userRepository.findById(user2Id).orElseThrow { RuntimeException("Пользователь не найден") }

        val newChat = chatRepository.save(Chat(user1 = user1, user2 = user2))
        return newChat.id
    }

    fun deleteChat(id: Long): Boolean{
        return if (chatRepository.existsById(id)) {
            chatRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun getAllChats(): List<Chat>{
        return chatRepository.findAll()
    }

    fun getChatById(id: Long): Chat? {
        return chatRepository.findById(id).orElse(null)
    }
}