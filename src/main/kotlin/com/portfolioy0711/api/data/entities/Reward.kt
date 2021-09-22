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
): Base() {
    private constructor(builder: Builder): this(builder.rewardId, builder.reviewId, builder.user, builder.operation, builder.pointDelta, builder.reason)

    override fun toString(): String {
        return """Reward(rewardId: ${rewardId}, reviewId:${reviewId}, operation: ${operation}, pointDelta: ${pointDelta}, reason: ${reason}})"""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reward

        if (rewardId != other.rewardId) return false
        if (reviewId != other.reviewId) return false
        if (user != other.user) return false
        if (operation != other.operation) return false
        if (pointDelta != other.pointDelta) return false
        if (reason != other.reason) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rewardId.hashCode()
        result = 31 * result + reviewId.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + operation.hashCode()
        result = 31 * result + pointDelta
        result = 31 * result + reason.hashCode()
        return result
    }


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


