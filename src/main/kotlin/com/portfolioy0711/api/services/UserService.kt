package com.portfolioy0711.api.services

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.User
import org.springframework.stereotype.Service

@Service
class UserService(private val eventDatabase: EventDatabase) {

    fun fetchUsers(): MutableIterable<User> {
        return eventDatabase
                .userModel
                .findUsers()
    }
}
