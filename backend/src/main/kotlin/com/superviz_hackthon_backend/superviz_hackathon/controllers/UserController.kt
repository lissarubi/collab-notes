package com.superviz_hackthon_backend.superviz_hackathon.controllers;

import com.superviz_hackthon_backend.superviz_hackathon.dto.UserDTO
import com.superviz_hackthon_backend.superviz_hackathon.models.User
import com.superviz_hackthon_backend.superviz_hackathon.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(private val service: UserService) {
    @GetMapping
    fun listUsers() = ResponseEntity.ok();

    @PostMapping
    fun post(@RequestBody message: UserDTO): ResponseEntity<User> {
        val savedUser = service.save(message)
        return ResponseEntity.created(URI("/${savedUser.id}")).body(savedUser)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<User> =
            service.findUserById(id).toResponseEntity()

    private fun User?.toResponseEntity(): ResponseEntity<User> =
            // If the message is null (not found), set response code to 404
            this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
}
