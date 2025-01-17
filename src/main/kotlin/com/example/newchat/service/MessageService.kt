package com.example.newchat.service
import com.example.newchat.entity.Message
import com.example.newchat.repository.ChatRepository
import com.example.newchat.repository.MessageRepository
import com.example.newchat.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MessageService(private val chatRepository: ChatRepository,
                     private val userRepository: UserRepository,
                     private val messageRepository: MessageRepository
){

    fun createMessage(user: Long, chat: Long, textMessage: String): Long{
        val user1 = userRepository.findById(user).orElseThrow { RuntimeException("Пользователь не найден") }
        val chat1 = chatRepository.findById(chat).orElseThrow { RuntimeException("Чат не найден") }
        val newMessage = messageRepository.save(Message(chat = chat1, user = user1, textMessage = textMessage))
        return newMessage.id
    }

    fun deleteMessage(id:Long): Boolean{
        return if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun getMessageByIdAndChatId(messageId: Long, chatId: Long): Message? {
        return messageRepository.findByIdAndChatId(messageId, chatId)
    }
    fun getMessagesByChatId(chatId: Long): List<Message> {
        return messageRepository.findByChatId(chatId)
    }
}