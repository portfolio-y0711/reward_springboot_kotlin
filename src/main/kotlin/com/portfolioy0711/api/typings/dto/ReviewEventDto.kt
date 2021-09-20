package com.portfolioy0711.api.typings.dto

data class ReviewEventDto(
    val type: String,
    val action: String,
    val reviewId: String,
    val content: String,
    val attachedPhotoIds: HashSet<String>,
    val userId: String,
    val placeId: String
) {
   class Builder {
      lateinit var type: String
      lateinit var action: String
      lateinit var reviewId: String
      lateinit var content: String
      lateinit var attachedPhotoIds: HashSet<String>
      lateinit var userId: String
      lateinit var placeId: String

      fun type(type: String) = apply { this.type = type }

      fun action(action: String) = apply { this.action = action }

      fun reviewId(reviewId: String) = apply { this.reviewId = reviewId }

      fun content(content: String) = apply { this.content = content }

      fun attachedPhotoIds(attachedPhotoIds: HashSet<String>) = apply { this.attachedPhotoIds = attachedPhotoIds }

      fun userId(userId: String) = apply { this.userId = userId }

      fun placeId(placeId: String) = apply { this.placeId = placeId }

      fun build() = ReviewEventDto(type, action, reviewId, content, attachedPhotoIds, userId, placeId)
   }
}

