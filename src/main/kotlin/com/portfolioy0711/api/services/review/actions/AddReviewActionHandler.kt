package com.portfolioy0711.api.services.review.actions

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.Photo
import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.data.entities.Reward
import com.portfolioy0711.api.services.review.ActionHandler
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.DuplicateRecordException
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class AddReviewActionHandler(val eventDatabase: EventDatabase) : ActionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun handleEvent(event: Any) {
        val eventInfo = event as ReviewEventDto
        val reviewModel = eventDatabase.reviewModel
        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.action));

        val isDuplicate: Boolean = reviewModel.checkRecordExistsByReviewId(eventInfo.reviewId)

        if (isDuplicate) {
            logger.error("""    ‣   process terminated: due to context error""");
            throw DuplicateRecordException("duplicate record exist by that reviewId")
        }

        val reviewCount = reviewModel.findReviewCountsByPlaceId(eventInfo.placeId)

        val isRewardable: Boolean = (reviewCount == 0L)

        logger.info("""    ‣    place id : ${eventInfo.placeId}""")
        logger.info("""    ‣    review counts: $reviewCount""")
        logger.info("""    ‣    review rewardable?: ${if (isRewardable) "YES" else "NO"}""")
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

            logger.info("""+ content point: $contentPoint""")
            logger.info("""+ photos point: $photosPoint""")
            logger.info("""= total point : $addPoint""")

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
