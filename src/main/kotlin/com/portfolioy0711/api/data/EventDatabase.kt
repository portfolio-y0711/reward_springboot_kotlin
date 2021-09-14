package com.portfolioy0711.api.data

import com.portfolioy0711.api.data.models.PlaceModel
import com.portfolioy0711.api.data.models.UserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
data class EventDatabase(
    @Autowired val userModel: UserModel,
    @Autowired val placeModel: PlaceModel
)

