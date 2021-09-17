package com.portfolioy0711.api.data.models.user

import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserQueryRepositoryImpl(
   val query: JPAQueryFactory
): UserQueryRepository {

    override fun findUserRewardPoint(userId: String): Int {
        val user = QUser.user;
     return query
             .select(user.rewardPoint)
             .from(user)
             .where(user.userId.eq(userId))
             .fetchOne()!!
    }

    override fun findUserByUserId(userId: String): User {
        val user = QUser.user;
        return query
                .select(user)
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne()!!
    }
}
