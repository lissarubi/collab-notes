package com.superviz_hackthon_backend.superviz_hackathon.services

import com.superviz_hackthon_backend.superviz_hackathon.dto.UserDTO
import com.superviz_hackthon_backend.superviz_hackathon.models.User
import com.superviz_hackthon_backend.superviz_hackathon.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(private val db: UserRepository) {
    fun findUsers(): List<User> = db.findAll().toList()

    fun findUserById(id: String): User? = db.findByIdOrNull(id)


    fun save(message: UserDTO): User {
        val user = User(name = message.name)
        return db.save(user)
    }

}