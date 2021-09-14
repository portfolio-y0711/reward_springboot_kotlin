package com.portfolioy0711.api.data.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(
        @Id @Column(name = "userId") var userId: String,
        @Column(nullable = false) var name: String,
        @Column(nullable = false ) var rewardPoint: Int
)

