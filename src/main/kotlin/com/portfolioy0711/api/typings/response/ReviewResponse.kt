package com.portfolioy0711.api.typings.response

data class ReviewResponse(
       val reviewId: String,
       val placeId: String,
       val userId: String,
       val content: String,
       val rewarded: Int,
       val photoIds: Set<String>
)
