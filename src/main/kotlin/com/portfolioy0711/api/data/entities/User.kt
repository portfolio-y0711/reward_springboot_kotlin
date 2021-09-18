package com.portfolioy0711.api.data.entities

import javax.persistence.*
import kotlin.properties.Delegates

@Entity
@Table(name = "user")
data class User(
        @Id @Column(name = "userId") val userId: String,
        @Column(nullable = false) val name: String,
        @Column(nullable = false ) val rewardPoint: Int,
        @OneToMany(mappedBy = "user")
        val reviews: Set<Review> = setOf()
) {
    private constructor(builder: Builder): this(builder.userId, builder.name, builder.rewardPoint)

    class Builder {
        lateinit var userId: String
            private set

        lateinit var name: String
            private set

        var rewardPoint by Delegates.notNull<Int>()
            private set

//        lateinit var reviews: Set<Review>
//            private set

        fun userId(userId: String) = apply { this.userId = userId }
        fun name(name: String) = apply { this.name = name }
        fun rewardPoint(rewardPoint: Int) = apply { this.rewardPoint = rewardPoint }
//        fun reviews(reviews: Set<Review>) = apply { this.reviews = reviews }

        fun build() = User(userId, name, rewardPoint)
    }
}

