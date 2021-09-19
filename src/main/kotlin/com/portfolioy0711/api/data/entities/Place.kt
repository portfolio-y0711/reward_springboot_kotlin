package com.portfolioy0711.api.data.entities

import javax.persistence.*
import kotlin.properties.Delegates

@Entity
@Table(name = "place")
data class Place (
    @Id val placeId: String,
    @Column val name: String,
    @Column val country: String,
    @Column val bonusPoint: Int,
    @OneToMany(mappedBy = "place")
    val reviews: Set<Review> = setOf()
) {
   private constructor(builder: Builder): this(builder.placeId, builder.name, builder.country, builder.bonusPoint)

   override fun toString(): String {
      return """Place(placeId: ${placeId}, name:${name}, country: ${country}, bonusPoint: ${bonusPoint})"""
   }

   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Place

      if (placeId != other.placeId) return false
      if (name != other.name) return false
      if (country != other.country) return false
      if (bonusPoint != other.bonusPoint) return false

      return true
   }

   override fun hashCode(): Int {
      var result = placeId.hashCode()
      result = 31 * result + name.hashCode()
      result = 31 * result + country.hashCode()
      result = 31 * result + bonusPoint
      return result
   }


   class Builder {
      lateinit var placeId: String
      lateinit var name: String
      lateinit var country: String
      var bonusPoint by Delegates.notNull<Int>()
//      lateinit var reviews: Set<Review>

      fun placeId(placeId: String) = apply { this.placeId = placeId }
      fun name(name: String) = apply { this.name = name }
      fun country(country: String) = apply { this.country = country }
      fun bonusPoint(bonusPoint: Int) = apply { this.bonusPoint = bonusPoint }
//      fun reviews(reviews: Set<Review>) = apply { this.reviews = reviews }

      fun build() = Place(placeId, name, country, bonusPoint)
   }
}

