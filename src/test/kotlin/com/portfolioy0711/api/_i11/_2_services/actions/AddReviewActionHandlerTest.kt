package com.portfolioy0711.api._i11._2_services.actions

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.*
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.DuplicateRecordException
import org.junit.Ignore
import org.junit.jupiter.api.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AddReviewActionHandlerTest {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var eventDatabase: EventDatabase

    @BeforeAll
    internal fun setUp() {
        val placeModel = eventDatabase.placeModel
        placeModel.save(
            Place.Builder()
                    .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                    .country("호주")
                    .name("멜번")
                    .bonusPoint(0)
                    .build()
        )
        val userModel = eventDatabase.userModel
        userModel.save(
            User.Builder()
                    .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                    .name("Michael")
                    .rewardPoint(3)
                    .build()
        )

    }

    @Test
    fun `AddReviewActionHandler should handle "ADD" action of "REVIEW" type event`() {
        val eventInfo: ReviewEventDto = ReviewEventDto
                .Builder()
                .type("REVIEW")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .action("ADD")
                .content("좋아요")
                .attachedPhotoIds(
                    hashSetOf(
                        "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                    )
                )
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()

        val reviewModel = eventDatabase.reviewModel
        logger.info("""[EVENT: ReviewEventActionHandler (${eventInfo.action})] started process ========================START""")

        val isDuplicate: Boolean = reviewModel.checkRecordExistsByReviewId(eventInfo.reviewId)

        if (isDuplicate) {
            logger.error("""    ‣ process terminated: due to context error""");
            throw DuplicateRecordException("duplicate record exist by that ")
        }

        val reviewCount = reviewModel.findReviewCountsByPlaceId(eventInfo.placeId)

        val isRewardable: Boolean = (reviewCount == 0L)

        logger.info("""    ‣ place id : ${eventInfo.placeId}""")
        logger.info("""    ‣ review counts: $reviewCount""")
        logger.info("""    ‣ review rewardable?: ${if (isRewardable) "YES" else "NO"}""")
        logger.info("""    transaction started ------------------------------------BEGIN""")

        val placeModel = eventDatabase.placeModel
        val place = placeModel.findPlaceByPlaceId(eventInfo.placeId)

        val userModel = eventDatabase.userModel
        val user = userModel.findUserByUserId(eventInfo.userId)

        val review = reviewModel.save(
                Review.Builder()
                        .reviewId(eventInfo.reviewId)
                        .content(eventInfo.content)
                        .rewarded(1)
                        .user(user)
                        .place(place)
                        .build()
        )

        logger.info("""    [✔︎] REVIEWS review has been created""")

        if (isRewardable) {
            val bonusPoint = place.bonusPoint

            logger.info("""    calculate review points""")
            logger.info("""    ‣ bonusPoint: $bonusPoint""")

            val contentPoint = if (eventInfo.content.length > 1) 1 else 0
            val photosPoint = if (eventInfo.attachedPhotoIds.size > 1) 1 else 0

            val addPoint = contentPoint + photosPoint + bonusPoint

            logger.info("""    + content point: $contentPoint""")
            logger.info("""    + photos point: $photosPoint""")
            logger.info("""    = total point : $addPoint""")

            val currPoint = userModel.findUserRewardPoint(eventInfo.userId)

            logger.info("""    ‣ user's current rewards point: $currPoint""")
            logger.info("""    ‣ user's next rewards point: ${currPoint + addPoint}""")

            val addOperation = "ADD"
            val addReason = "NEW"

            val rewardModel = eventDatabase.rewardModel

            rewardModel.save(
                Reward
                    .Builder()
                    .rewardId(UUID.randomUUID().toString())
                    .reviewId(eventInfo.reviewId)
                    .operation(addOperation)
                    .pointDelta(currPoint + addPoint)
                    .reason(addReason)
                    .user(user)
                    .build()
            )

            logger.info("""    [✔︎] REWARDS %s record created", addOperation""")
            val photoModel = eventDatabase.photoModel
            val photoIds = eventInfo.attachedPhotoIds

            photoIds.stream()
                .map{ photoId ->
                    Photo.Builder()
                        .photoId(photoId)
                        .review(review)
                        .build()
                }
                .forEach(photoModel::save)
        }
    }
}
