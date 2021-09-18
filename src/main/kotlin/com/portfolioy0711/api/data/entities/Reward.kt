package com.portfolioy0711.api.data.entities

import javax.persistence.*
import kotlin.properties.Delegates

@Entity
data class Reward(
    @Id val rewardId: String,
    @Column val reviewId: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false) val user: User,
    @Column val operation: String,
    @Column val pointDelta: Int,
    @Column val reason: String
) {
    private constructor(builder: Reward.Builder): this(builder.rewardId, builder.reviewId, builder.user, builder.operation, builder.pointDelta, builder.reason)

    class Builder {
        lateinit var rewardId: String
        lateinit var reviewId: String
        lateinit var user: User
        lateinit var operation: String
        var pointDelta by Delegates.notNull<Int>()
        lateinit var reason: String

        fun rewardId(rewardId: String) = apply { this.rewardId = rewardId }
        fun reviewId(reviewId: String) = apply { this.reviewId = reviewId }
        fun user(user: User) = apply { this.user = user }
        fun operation(operation: String) = apply { this.operation = operation }
        fun pointDelta(pointDelta: Int) = apply { this.pointDelta = pointDelta }
        fun reason(reason: String) = apply { this.reason = reason }
        fun build() = Reward(rewardId, reviewId, user, operation, pointDelta, reason)
    }
}


