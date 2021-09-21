package com.portfolioy0711.api.typings.response

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class UserRewardResponse @QueryProjection constructor(
    val rewardId: String,
    val userId: String,
    val reviewId: String,
    val operation: String,
    val pointDelta: Int,
    val reason: String,
    val created_at: LocalDateTime
)

