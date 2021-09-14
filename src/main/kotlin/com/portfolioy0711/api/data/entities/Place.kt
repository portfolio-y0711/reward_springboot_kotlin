package com.portfolioy0711.api.data.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "place")
data class Place (
    @Id val placeId: String,
    @Column val name: String,
    @Column val country: String,
    @Column val bonusPoint: Int
)

