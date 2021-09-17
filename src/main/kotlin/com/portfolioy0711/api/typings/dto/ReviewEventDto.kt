package com.portfolioy0711.api.typings.dto

class ReviewEventDto(
    val type: String,
    val action: String,
    val reviewId: String,
    val content: String,
    val attachedPhotoIds: Array<String>,
    val userId: String,
    val placeId: String
)

