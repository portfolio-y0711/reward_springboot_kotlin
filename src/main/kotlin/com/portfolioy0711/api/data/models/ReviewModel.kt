package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.QReview
import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository
import com.portfolioy0711.api.data.models.review.ReviewQueryRepository
import com.portfolioy0711.api.typings.response.ReviewResponse
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun save(review: Review): Review {
       return reviewCmdRepository.save(review)
    }

    fun findReviewInfoByReviewId(reviewId: String): ReviewResponse? {
        return reviewQueryRepository.findReviewInfoByReviewId(reviewId)
    }

    fun findReviewByReviewId(reviewId: String): Review {
        return reviewQueryRepository.findReviewByReviewId(reviewId)
    }

    @Transactional
    fun updateReview(reviewId: String, content: String): Long {
        return reviewQueryRepository.updateReview(reviewId, content)
    }

    fun remove(reviewId: String) {
        return reviewCmdRepository.deleteById(reviewId)
    }

    @Transactional
    fun deleteAll() {
        return reviewCmdRepository.deleteAll()
    }
}
