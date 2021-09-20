package com.portfolioy0711.api.data

import com.portfolioy0711.api.data.models.*
import com.portfolioy0711.api.data.models.PhotoModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
data class EventDatabase(
    @Autowired val userModel: UserModel,
    @Autowired val placeModel: PlaceModel,
    @Autowired val reviewModel: ReviewModel,
    @Autowired val rewardModel: RewardModel,
    @Autowired val photoModel: PhotoModel
)


