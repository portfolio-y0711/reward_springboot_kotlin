package com.portfolioy0711.api.data.models.review

import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.typings.response.ReviewResponse

interface ReviewQueryRepository {
    fun checkRecordExistsByReviewId(reviewId: String): Boolean
    fun findReviewCountsByPlaceId(placeId: String): Long
    fun findReviewInfoByReviewId(reviewId: String): ReviewResponse?
    fun findReviewByReviewId(reviewId: String): Review?
    fun updateReview(reviewId: String, content: String): Long
}
