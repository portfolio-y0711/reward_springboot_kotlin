package com.portfolioy0711.api.data.models.reward

import com.portfolioy0711.api.data.entities.Reward

interface RewardQueryRepository {
    fun findLatestUserReviewRewardByReviewId(userId: String, reviewId: String): Reward
}
