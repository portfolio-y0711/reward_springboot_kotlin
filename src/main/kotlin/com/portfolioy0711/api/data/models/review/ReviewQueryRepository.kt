package com.portfolioy0711.api.data.models.review

interface ReviewQueryRepository {
    fun checkRecordExistsByReviewId(reviewId: String): Boolean
    fun findReviewCountsByPlaceId(placeId: String): Int
}
