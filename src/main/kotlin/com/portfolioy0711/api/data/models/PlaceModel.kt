package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository
import com.portfolioy0711.api.data.models.place.PlaceQueryRepository
import org.springframework.stereotype.Component

@Component
class PlaceModel (
    val placeCmdRepository: PlaceCmdRepository,
    val placeQueryRepository: PlaceQueryRepository
) {
    fun save(place: Place): Place {
       return placeCmdRepository.save(place)
    }

    fun findPlaceByPlaceId(placeId: String): Place {
        return placeQueryRepository.findPlaceByPlaceId(placeId)
    }
}

