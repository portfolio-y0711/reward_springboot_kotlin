package com.portfolioy0711.api.data.models.user

import com.portfolioy0711.api.data.entities.User
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Component

interface UserQueryRepository {
    fun findUsers(): MutableList<User>
}
