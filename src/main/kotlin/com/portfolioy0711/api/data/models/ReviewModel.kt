package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository
import com.portfolioy0711.api.data.models.review.ReviewQueryRepository
import com.portfolioy0711.api.typings.response.ReviewResponse
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashSet
import kotlin.streams.toList

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

    fun findReviewInfoByReviewId_native(reviewId: String): ReviewResponse {
        val result = reviewCmdRepository.findReviewInfoByReviewId_native(reviewId)
        var photoIds: HashSet<String>? = null
        if (result[0][5] != null) {
            photoIds = Arrays.stream(result).map {
                it -> it[5] as String
            }.toList().toHashSet()
        }
        return ReviewResponse(result[0][0] as String, result[0][1] as String, result[0][2] as String, result[0][3] as String, result[0][4] as Int, if (photoIds == null) hashSetOf() else photoIds)
    }

    @Transactional
    fun findReviewByReviewId(reviewId: String): Review? {
        return reviewQueryRepository.findReviewByReviewId(reviewId)
    }

    @Transactional
    fun updateReview(reviewId: String, content: String): Long {
        return reviewQueryRepository.updateReview(reviewId, content)
    }

    @Transactional
    fun remove(reviewId: String) {
        return reviewCmdRepository.deleteById(reviewId)
    }

    @Transactional
    fun deleteAll() {
        return reviewCmdRepository.deleteAll()
    }
}
