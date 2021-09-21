package com.portfolioy0711.api.data.entities

import javax.persistence.*
import kotlin.properties.Delegates

@Entity
@Table(name = "review")
data class Review(
        @Id val reviewId: String,

        @ManyToOne(targetEntity = Place::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "placeId", nullable = false)
        val place: Place,

        @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "userId", nullable = false)
        val user: User,

        @Column val content: String,
        @Column val rewarded: Int,
        @OneToMany(mappedBy = "review")
        val photos: Set<Photo> = hashSetOf()

): Base() {

    override fun toString(): String {
        return """Review(reviewId: ${reviewId}, place:${place.placeId}, user: ${user.userId}, content: ${content}, rewarded: ${rewarded}, photos: ${photos.toString()})"""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Review

        if (reviewId != other.reviewId) return false
        if (place != other.place) return false
        if (user != other.user) return false
        if (content != other.content) return false
        if (rewarded != other.rewarded) return false
        if (photos != other.photos) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reviewId.hashCode()
        result = 31 * result + place.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + rewarded
        result = 31 * result + photos.hashCode()
        return result
    }


    private constructor(builder: Builder) : this(builder.reviewId, builder.place, builder.user, builder.content, builder.rewarded)

    class Builder {
        lateinit var reviewId: String
            private set
        lateinit var place: Place
            private set

        lateinit var user: User
            private set

        lateinit var content: String
            private set
        var rewarded by Delegates.notNull<Int>()
            private set

        fun reviewId(reviewId: String) = apply { this.reviewId = reviewId }
        fun place(place: Place) = apply { this.place = place }
        fun user(user: User) = apply { this.user = user }
        fun content(content: String) = apply { this.content = content }
        fun rewarded(rewarded: Int) = apply { this.rewarded = rewarded }
        fun build() = Review(reviewId, place, user, content, rewarded)
    }
}
