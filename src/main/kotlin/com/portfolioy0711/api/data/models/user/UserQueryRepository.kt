package com.portfolioy0711.api.data.models.user

import com.portfolioy0711.api.data.entities.User

interface UserQueryRepository {
    fun findUserRewardPoint(userId: String): Int
    fun findUserByUserId(userId: String): User
}
