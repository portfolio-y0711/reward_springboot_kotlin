package com.portfolioy0711.api.data.models.reward

import com.portfolioy0711.api.data.entities.QReview
import com.portfolioy0711.api.data.entities.QReward
import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.Reward
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

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

}
