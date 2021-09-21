package com.portfolioy0711.api.data.models.reward

import com.portfolioy0711.api.data.entities.Reward
import com.portfolioy0711.api.typings.response.UserRewardResponse

interface RewardQueryRepository {
    fun findLatestUserReviewRewardByReviewId(userId: String, reviewId: String): Reward
    fun findRewardsByUserId(userId: String): MutableList<UserRewardResponse>?
}
