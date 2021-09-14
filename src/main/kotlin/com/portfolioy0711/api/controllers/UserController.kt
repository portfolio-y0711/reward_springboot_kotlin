package com.portfolioy0711.api.controllers

import com.portfolioy0711.api.data.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/healthCheck")
    fun healthCheck(): String {
       return "ok"
    }

    @GetMapping("/users")
    fun users(): MutableMap<String, String> {
        val user = hashMapOf<String, String>(
                "uuid" to "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "name" to "michael"
        )
        userRepository.findUsers()
        return user
    }
}
