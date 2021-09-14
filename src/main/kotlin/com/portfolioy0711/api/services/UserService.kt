package com.portfolioy0711.api.services

import com.portfolioy0711.api.data.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun fetchUsers(): HashMap<String, String> {
        val user = hashMapOf<String, String>(
                "uuid" to "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "name" to "michael"
        )
        userRepository.findUsers()
        return user
    }
}
