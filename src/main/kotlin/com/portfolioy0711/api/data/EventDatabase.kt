package com.portfolioy0711.api.data

import com.portfolioy0711.api.data.models.user.UserModel
import org.springframework.stereotype.Component

@Component
data class EventDatabase(
    val userModel: UserModel
)

