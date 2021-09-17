package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository
import org.springframework.stereotype.Component

@Component
class PlaceModel (
    val placeCmdRepository: PlaceCmdRepository,
) {
    fun save(place: Place): Place {
       return placeCmdRepository.save(place)
    }
}
