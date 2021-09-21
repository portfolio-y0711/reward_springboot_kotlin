package com.portfolioy0711.api.data.models.reward

import com.portfolioy0711.api.data.entities.QReview
import com.portfolioy0711.api.data.entities.QReward
import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.Reward
import com.portfolioy0711.api.typings.response.QUserRewardResponse
import com.portfolioy0711.api.typings.response.UserRewardResponse
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.management.Query

@Component
class RewardQueryRepositoryImpl: RewardQueryRepository {

    @Autowired
    lateinit var query: JPAQueryFactory

    override fun findLatestUserReviewRewardByReviewId(userId: String, reviewId: String): Reward {
        val reward = QReward.reward
        val user = QUser.user

        return query.select(reward)
                .from(reward)
                .join(reward.user, user)
                .where(reward.user.userId.eq(userId))
                .limit(1)
                .fetchOne()!!
    }

    override fun findRewardsByUserId(userId: String): MutableList<UserRewardResponse>? {
        val reward = QReward.reward
        val user = QUser.user
        return query.select(QUserRewardResponse(reward.rewardId, reward.user.userId, reward.reviewId, reward.operation, reward.pointDelta, reward.reason, reward.createdAt))
                .from(reward)
                .join(reward.user, user)
                .where(reward.user.userId.eq(userId))
                .fetch()
    }

}
