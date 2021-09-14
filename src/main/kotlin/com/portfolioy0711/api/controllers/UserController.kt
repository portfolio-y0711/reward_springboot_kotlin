package com.portfolioy0711.api.controllers

import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getUsers(): MutableIterable<User> {
        val user = userService.fetchUsers()
        return user
    }

}
