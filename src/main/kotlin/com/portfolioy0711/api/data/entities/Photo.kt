package com.portfolioy0711.api.data.entities

import javax.persistence.*

@Entity
data class Photo(
        @Id val photoId: String,
        @ManyToOne(targetEntity = Review::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "reviewId", nullable = true)
        val review: Review
): Base() {
    override fun toString(): String {
        return """Photo(placeId: ${photoId})"""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (photoId != other.photoId) return false

        return true
    }

    override fun hashCode(): Int {
        return photoId.hashCode()
    }


    private constructor(builder: Builder) : this(builder.photoId, builder.review)

    class Builder {

        lateinit var photoId: String
        lateinit var review: Review

        fun photoId(photoId: String) = apply { this.photoId = photoId }
        fun review(review: Review) = apply { this.review = review }

        fun build() = Photo(photoId, review)
    }
}
