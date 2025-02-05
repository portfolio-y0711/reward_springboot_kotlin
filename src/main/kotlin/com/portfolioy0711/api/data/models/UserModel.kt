package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.data.models.user.UserCmdRepository
import com.portfolioy0711.api.data.models.user.UserQueryRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserModel(
    val userCmdRepository: UserCmdRepository,
    val userQueryRepository: UserQueryRepository
) {

    @Transactional
    fun save (user: User): User {
        return userCmdRepository.save(user)
    }

    fun findUserRewardPoint(userId: String): Int {
        return userQueryRepository.findUserRewardPoint(userId)
    }

    fun findUserByUserId(userId: String): User {
        return userQueryRepository.findUserByUserId(userId)
    }

    @Transactional
    fun updateRewardPoint(userId: String, rewardPoint: Int): Long {
        return userQueryRepository.updateRewardPoint(userId, rewardPoint)
    }

    @Transactional
    fun deleteAll() {
        return userCmdRepository.deleteAll()
    }
}
