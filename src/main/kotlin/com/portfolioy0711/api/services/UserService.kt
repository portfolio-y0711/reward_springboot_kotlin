package com.portfolioy0711.api.services

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.typings.response.UserRewardResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val eventDatabase: EventDatabase) {

    fun fetchUserRewardPoint(userId: String): Int {
        return eventDatabase
                .userModel
                .findUserRewardPoint(userId)
    }

    fun fetchUserRewards(userId: String): MutableList<UserRewardResponse>? {
        return eventDatabase
                .rewardModel
                .findRewardsByUserId(userId)
    }
}
