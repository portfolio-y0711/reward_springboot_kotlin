package com.portfolioy0711.api._i11._1_databases

import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.entities.QReview
import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.data.models.ReviewModel
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository
import com.portfolioy0711.api.data.models.user.UserCmdRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import junit.framework.Assert.assertEquals
import org.junit.Ignore
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class ReviewModelTest {
    @Autowired
    lateinit var reviewCmd: ReviewCmdRepository

    @Autowired
    lateinit var placeCmd: PlaceCmdRepository

    @Autowired
    lateinit var userCmd: UserCmdRepository

    @Autowired
    lateinit var query: JPAQueryFactory

    @Autowired
    lateinit var reviewModel: ReviewModel


    @Test
    @Transactional
    internal fun `reviewModel_checkRecordExistsByReviewId`() {

        val placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        val place = placeCmd.save(
            Place.Builder()
                .placeId(placeId)
                .country("호주")
                .bonusPoint(0)
                .name("멜번")
                .build()
        )

        val userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745"
        val user = userCmd.save(
            User.Builder()
                .userId(userId)
                .name("Michael")
                .rewardPoint(0)
                .build()
        )


        val reviewId = "240a0658-dc5f-4878-9831-ebb7b26687772"
        reviewCmd.save(
            Review.Builder()
                .reviewId(reviewId)
                .user(user)
                .place(place)
                .content("좋아요")
                .rewarded(1)
                .build()
        )

        val review = QReview.review

        val expected = true
        val actual = query.select(review)
            .from(review)
            .where(review.reviewId.eq(reviewId))
            .fetchCount() > 0

        assertEquals(expected, actual)
    }

    @Test
    @Ignore
    @Transactional
    internal fun `reviewModel_findReviewCountsByPlaceId`() {
        val review = QReview.review
        val placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f"

        val expected = 3

        val actual = query.select(review)
            .where(review.place.placeId.eq(placeId))
            .fetchCount()

        assertEquals(expected, actual)

    }

    @Test
    internal fun `reviewModel_save`() {


    }
}

