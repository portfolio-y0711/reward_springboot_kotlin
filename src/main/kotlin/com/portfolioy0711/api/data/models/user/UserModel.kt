package com.portfolioy0711.api.data.models.user

import com.portfolioy0711.api.data.entities.User
import org.springframework.stereotype.Component

@Component
class UserModel(
    val userCmdRepository: UserCmdRepository,
    val userQueryRepository: UserQueryRepository
) {

    fun save (user: User): User {
        return userCmdRepository.save(user)
    }

    fun findUsers(): MutableIterable<User> {
        return userQueryRepository.findUsers()
    }
}
