package com.portfolioy0711.api.data.entities

import javax.persistence.*

@Entity
data class Photo(
        @Id val photoId: String,
        @ManyToOne(targetEntity = Review::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "reviewId", nullable = true)
        val review: Review
) {
    private constructor(builder: Builder) : this(builder.photoId, builder.review)

    class Builder {

        lateinit var photoId: String
        lateinit var review: Review

        fun photoId(photoId: String) = apply { this.photoId = photoId }
        fun review(review: Review) = apply { this.review = review }

        fun build() = Photo(photoId, review)
    }
}
