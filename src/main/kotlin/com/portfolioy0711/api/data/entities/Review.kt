package com.portfolioy0711.api.data.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import kotlin.properties.Delegates

@Entity
@Table(name = "review")
data class Review(
    @Id val reviewId: String,
    @Column val content: String,
    @Column val rewarded: Int
) {
    private constructor(builder: Builder): this(builder.reviewId, builder.content, builder.rewarded)

    class Builder {
        lateinit var reviewId: String
            private set
        lateinit var content: String
            private set
        var rewarded by Delegates.notNull<Int>()
            private set

        fun reviewId(reviewId: String) = apply { this.reviewId = reviewId }

        fun content(content: String) = apply { this.content = content }

        fun rewarded(rewarded: Int) = apply { this.rewarded = rewarded }

        fun build() = Review(reviewId, content, rewarded)
    }
}
