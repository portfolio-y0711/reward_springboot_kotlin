package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Reward
import com.portfolioy0711.api.data.models.reward.RewardCmdRepository
import com.portfolioy0711.api.data.models.reward.RewardQueryRepository
import org.springframework.stereotype.Component

@Component
class RewardModel(
        val rewardCmdRepository: RewardCmdRepository,
        val rewardQueryRepository: RewardQueryRepository
) {
    fun save(reward: Reward): Reward {
        return rewardCmdRepository.save(reward)
    }
    fun findLatestUserReviewRewardByReviewId(userId: String, reviewId: String): Reward {
        return rewardQueryRepository.findLatestUserReviewRewardByReviewId(userId, reviewId)
    }
}
