package com.portfolioy0711.api.data.models.user

interface UserQueryRepository {
    fun findUserRewardPoint(userId: String): Int
}
