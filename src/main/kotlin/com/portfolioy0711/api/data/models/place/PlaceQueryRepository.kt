package com.portfolioy0711.api.data.models.place

import com.portfolioy0711.api.data.entities.Place

interface PlaceQueryRepository {
    fun findPlaceByPlaceId(placeId: String): Place
}
