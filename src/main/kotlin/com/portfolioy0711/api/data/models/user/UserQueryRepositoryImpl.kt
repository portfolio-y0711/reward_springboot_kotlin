package com.portfolioy0711.api.data.models.user

import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserQueryRepositoryImpl(
       @Autowired val query: JPAQueryFactory
): UserQueryRepository {
    override fun findUsers(): MutableList<User> {
        val user = QUser.user;
        return query.select(user)
                .from(user)
                .fetch()!!
    }
}
