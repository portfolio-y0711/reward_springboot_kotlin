package com.portfolioy0711.api.services

import com.portfolioy0711.api.data.UserRepository
import com.portfolioy0711.api.data.entities.User
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

//    fun fetchUsers(): MutableList<User> {
//        return userRepository.findUsers()
//    }
}
