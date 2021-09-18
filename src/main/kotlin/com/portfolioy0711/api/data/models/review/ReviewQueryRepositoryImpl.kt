package com.portfolioy0711.api.data.models.review

import com.portfolioy0711.api.data.entities.QReview
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ReviewQueryRepositoryImpl: ReviewQueryRepository {

    @Autowired
    lateinit var query: JPAQueryFactory

    override fun checkRecordExistsByReviewId(reviewId: String): Boolean {
        val review = QReview.review
        val result = query.select(review)
                .from(review)
                .where(review.reviewId.eq(reviewId))
                .fetchCount()
        return result > 0
    }

    override fun findReviewCountsByPlaceId(placeId: String): Int {
         return 0
    }
}
