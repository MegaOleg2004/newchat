package com.example.newchat.service
import com.example.newchat.entity.User
import com.example.newchat.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun createUser(name: String, age: Int): Long {
        val newUser = userRepository.save(User(name = name, age = age))
        return newUser.id
    }

    fun updateUser(id: Long, name: String, age: Int): Boolean {
        val existingUser = getUserById(id)
        if (existingUser == null) {
            return false
        }
        existingUser.name = name
        existingUser.age = age
        userRepository.save(existingUser)
        return true
    }

    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}