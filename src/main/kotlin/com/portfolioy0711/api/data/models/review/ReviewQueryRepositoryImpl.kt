package com.portfolioy0711.api.data.models.review

import com.portfolioy0711.api.data.entities.*
import com.portfolioy0711.api.typings.exception.NoRecordException
import com.portfolioy0711.api.typings.response.ReviewResponse
import com.querydsl.core.group.GroupBy
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.types.Projections
import org.springframework.transaction.annotation.Transactional


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

    override fun findReviewCountsByPlaceId(placeId: String): Long {
        val review = QReview.review
         return query.select(review)
                 .from(review)
                 .where(review.place.placeId.eq(placeId))
                 .fetchCount()
    }

    override fun findReviewInfoByReviewId(reviewId: String): ReviewResponse? {
        val review = QReview.review
        val place = QPlace.place
        val user = QUser.user
        val photo = QPhoto.photo

        var found: ReviewResponse

        try {
            found = query.select(review)
                    .from(review)
                    .where(review.reviewId.eq(reviewId))
                    .join(review.place, place)
                    .join(review.user, user)
                    .join(review.photos, photo)
                    .transform(
                        groupBy(review.reviewId)
                            .list(Projections.constructor(
                                    ReviewResponse::class.java,
                                    review.reviewId,
                                    place.placeId,
                                    user.userId,
                                    review.content,
                                    review.rewarded,
                                    GroupBy.set(photo.photoId)
                            ))
                    )[0]

        } catch (ex: IndexOutOfBoundsException) {
            return null
        }
        return found
    }

    override fun findReviewByReviewId(reviewId: String): Review {
        val review = QReview.review
        val found = query.select(review)
                .from(review)
                .where(review.reviewId.eq(reviewId))
                .fetchOne()!!
        println(found)
        return found
    }

    @Transactional
    override fun updateReview(reviewId: String, content: String): Long {
        val review = QReview.review
        return query.update(review)
            .set(review.content, content)
            .where(review.reviewId.eq(reviewId))
            .execute()

    }
}
