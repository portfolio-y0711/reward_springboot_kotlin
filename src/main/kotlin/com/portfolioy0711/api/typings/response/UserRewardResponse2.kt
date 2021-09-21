package com.portfolioy0711.api.typings.response

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class UserRewardResponse2 {
    val rewardId: String
    val userId: String
    val reviewId: String
    val operation: String
    val pointDelta: Int
    val reason: String
    val created_at: LocalDateTime

    @QueryProjection
    constructor(rewardId: String, userId: String, reviewId: String, operation: String, pointDelta: Int, reason: String, created_at: LocalDateTime) {
        this.rewardId = rewardId
        this.userId = userId
        this.reviewId = reviewId
        this.operation = operation
        this.pointDelta = pointDelta
        this.reason = reason
        this.created_at = created_at
    }
}
