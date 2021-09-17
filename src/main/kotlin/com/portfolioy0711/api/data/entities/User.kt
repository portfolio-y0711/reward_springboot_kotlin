package com.portfolioy0711.api.data.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import kotlin.properties.Delegates

@Entity
@Table(name = "user")
data class User(
        @Id @Column(name = "userId") var userId: String,
        @Column(nullable = false) var name: String,
        @Column(nullable = false ) var rewardPoint: Int
) {
    private constructor(builder: Builder): this(builder.userId, builder.name, builder.rewardPoint)

    class Builder {
        lateinit var userId: String
            private set

        lateinit var name: String
            private set

        var rewardPoint by Delegates.notNull<Int>()
            private set

        fun userId(userId: String) = apply { this.userId = userId }

        fun name(name: String) = apply { this.name = name }

        fun rewardPoint(rewardPoint: Int) = apply { this.rewardPoint = rewardPoint }

        fun build() = User(userId, name, rewardPoint)
    }
}

