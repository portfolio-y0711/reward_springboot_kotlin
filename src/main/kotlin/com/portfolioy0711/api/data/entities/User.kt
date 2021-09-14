package com.portfolioy0711.api.data.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
class User(
    @Id var userId: String,
    @Column var name: String,
    @Column var rewardPoint: Int
)

