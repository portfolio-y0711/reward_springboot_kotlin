package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository
import com.portfolioy0711.api.data.models.review.ReviewQueryRepository
import org.springframework.stereotype.Component

@Component
class ReviewModel(
        val reviewCmdRepository: ReviewCmdRepository,
        val reviewQueryRepository: ReviewQueryRepository
) {
    fun checkRecordExistsByReviewId(reviewId: String): Boolean {
        return reviewQueryRepository.checkRecordExistsByReviewId(reviewId)
    }

    fun findReviewCountsByPlaceId(placeId: String): Long {
       return reviewQueryRepository.findReviewCountsByPlaceId(placeId)
    }

    fun save(review: Review): Review {
       return reviewCmdRepository.save(review)
    }
}
