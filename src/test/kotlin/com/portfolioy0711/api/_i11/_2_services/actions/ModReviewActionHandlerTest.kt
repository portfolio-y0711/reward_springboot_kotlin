package com.portfolioy0711.api._i11._2_services.actions

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.*
import com.portfolioy0711.api.data.models.*
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.NoRecordException
import org.junit.Ignore
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.streams.toList

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModReviewActionHandlerTest {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var eventDatabase: EventDatabase

    @Autowired
    lateinit var userModel: UserModel

    @Autowired
    lateinit var placeModel: PlaceModel

    @Autowired
    lateinit var reviewModel: ReviewModel

    @Autowired
    lateinit var photoModel: PhotoModel

    @Autowired
    lateinit var rewardModel: RewardModel

    @BeforeAll
    internal fun setUp() {
        val _user = userModel.save(
                User.Builder()
                        .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                        .name("Michael")
                        .rewardPoint(3)
                        .build()
        )
        val _place = placeModel.save(
                Place.Builder()
                        .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                        .name("멜번")
                        .country("호주")
                        .bonusPoint(0)
                        .build()
        )
        val _review = reviewModel.save(
                Review.Builder()
                        .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                        .content("좋아요")
                        .user(_user)
                        .place(_place)
                        .rewarded(1)
                        .build()
        )
        photoModel.save(
                Photo.Builder()
                        .review(_review)
                        .photoId("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8")
                        .build()
        )

        photoModel.save(
                Photo.Builder()
                        .review(_review)
                        .photoId("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
                        .build()
        )

        val uuid = UUID.randomUUID()

        rewardModel.save(
                Reward.Builder()
                        .rewardId(uuid.toString())
                        .reviewId(_review.reviewId)
                        .user(_user)
                        .pointDelta(2)
                        .operation("ADD")
                        .reason("NEW")
                        .build()
        )
    }

    @Test
    internal fun `ModReviewActionHandler should handler "MOD" action of "REVIEW" type event `() {
        val eventInfo = ReviewEventDto
                .Builder()
                .type("REVIEW")
                .action("MOD")
                .content("좋아요")
                .attachedPhotoIds(hashSetOf(
                    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                ))
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .reviewId("240a0658-dc5f-4878-9831-ebb7b26687772")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .build()

        logger.info("""[EVENT: ReviewEventActionHandler (${eventInfo.action})] started process ========================START"""");
        val reviewModel = eventDatabase.reviewModel
        val found = reviewModel.findReviewInfoByReviewId(eventInfo.reviewId)

        if (found == null) {
            logger.error("""    ‣ no record exists by that reviewId""")
            throw NoRecordException("no record exists by that reviewId");
        }

        val isRewarded = found.rewarded == 1

        logger.info("""    ‣ review id : ${eventInfo.reviewId}""")
        logger.info("""    ‣ place id: ${eventInfo.placeId}""")
        logger.info("""    ‣ review rewarded?: ${if (isRewarded) "YES" else "NO"}""")

        if (isRewarded) {
            val rewardModel = eventDatabase.rewardModel
            val latestRewardRecord = rewardModel.findLatestUserReviewRewardByReviewId(eventInfo.userId, eventInfo.reviewId)

            logger.info("""    ‣ search latest reward record with 'userId' and 'reviewId'""")
            logger.info("""      reviewId: ${latestRewardRecord.reviewId}""")
            logger.info("""      reason: ${latestRewardRecord.reason} review""")
            logger.info("""      operation: ${latestRewardRecord.operation} ${latestRewardRecord.pointDelta}""")

            val place = placeModel.findPlaceByPlaceId(eventInfo.placeId)
            val bonusPoint = place.bonusPoint

            logger.info("""    ‣ bonusPoint: $bonusPoint""");

            val contentPoint = if (eventInfo.content.length > 1) 1 else 0
            val photosPoint = if (eventInfo.attachedPhotoIds.size > 1) 1 else 0
            val totalPoint = contentPoint + photosPoint + bonusPoint

            logger.info("""     + content point: $contentPoint""")
            logger.info("""     + photos point: $photosPoint""")
            logger.info("""     = total point: $totalPoint""")

            val diff = totalPoint - latestRewardRecord.pointDelta
            logger.info("""    ‣ point diff: $diff""")

            val isPointUpdatedNeeded = (diff != 0)

            if (isPointUpdatedNeeded) {
                val subtract_operation = "SUB"
                val subtract_reason = "MOD"

                val add_operation = "ADD"
                val add_reason = "MOD"

                val user = userModel.findUserByUserId(eventInfo.userId)
                val currPoint = user.rewardPoint

                logger.info("""    ‣ user's current rewardPoint: $currPoint""")
                logger.info("""    ‣ user's next rewardPoint: ${currPoint + diff}""")

                logger.info("   transaction started ------------------------------------BEGIN")

                rewardModel.save(
                    Reward.Builder()
                            .rewardId(UUID.randomUUID().toString())
                            .user(user)
                            .reason(subtract_reason)
                            .operation(subtract_operation)
                            .pointDelta(currPoint)
                            .build()
                )

                rewardModel.save(
                    Reward.Builder()
                            .rewardId(UUID.randomUUID().toString())
                            .user(user)
                            .reason(add_reason)
                            .operation(add_operation)
                            .pointDelta(totalPoint)
                            .build()
                )

                logger.info("""    [✔︎] REWARDS $subtract_operation record created""")
                logger.info("""    [✔︎] REWARDS $add_operation record created""")

                userModel.updateRewardPoint(
                        eventInfo.userId,
                        currPoint + diff
                )
                logger.info("""    [✔︎] USERS total rewardPoint updated""")
            }
        } else {
            logger.info("\ttransaction started ------------------------------------BEGIN");
        }

        val currentReview = reviewModel.findReviewInfoByReviewId(eventInfo.reviewId)
        val currentPhotoIds = currentReview!!.photoIds.toList()

        val newPhotoIds = eventInfo.attachedPhotoIds.toList()

        val add_photo_ids: Array<String> = eventInfo.attachedPhotoIds.stream()
                .filter { photoId ->  currentPhotoIds.contains(photoId )}
                .toList().toTypedArray()

        val delete_photo_ids: Array<String> = currentPhotoIds.stream()
                .filter{ currentPhotoId -> !newPhotoIds.contains(currentPhotoId) }
                .toList().toTypedArray()

        if (add_photo_ids.size > 0 || delete_photo_ids.size > 0) {
            val review = reviewModel.findReviewByReviewId(eventInfo.reviewId)!!

            Arrays.stream(add_photo_ids)
                    .map{photoId -> Photo(photoId, review)}
                    .forEach(photoModel::save)

            Arrays.stream(delete_photo_ids)
                    .forEach(photoModel::remove)

            logger.info("""    [✔︎] PHOTOS has been updated""")
        }

        reviewModel.updateReview(
                eventInfo.reviewId,
                eventInfo.content
        )

        logger.info("""    [✔︎] REVIEWS review has been updated""")
        logger.info("""    transaction finished -------------------------------------END""")

        logger.info("===================================================================================END")
    }
}
