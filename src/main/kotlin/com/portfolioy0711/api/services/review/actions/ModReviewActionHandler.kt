package com.portfolioy0711.api.services.review.actions

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.Photo
import com.portfolioy0711.api.data.entities.Reward
import com.portfolioy0711.api.services.review.ActionHandler
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.NoRecordException
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.streams.toList

open class ModReviewActionHandler(val eventDatabase: EventDatabase) : ActionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun handleEvent(event: Any) {
        val eventInfo = event as ReviewEventDto

        logger.info("""[EVENT: ReviewEventActionHandler (${eventInfo.action})] started process ========================START"""");
        val reviewModel = eventDatabase.reviewModel
        val found = reviewModel.findReviewByReviewId(eventInfo.reviewId)

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

            val placeModel = eventDatabase.placeModel
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

            val isPointUpdateNeeded = (diff != 0)

            if (isPointUpdateNeeded) {
                val subtract_operation = "SUB"
                val subtract_reason = "MOD"

                val add_operation = "ADD"
                val add_reason = "MOD"

                val userModel = eventDatabase.userModel
                val user = userModel.findUserByUserId(eventInfo.userId)
                val currPoint = user.rewardPoint

                logger.info("""    ‣ user's current rewardPoint: $currPoint""")
                logger.info("""    ‣ user's next rewardPoint: ${currPoint + diff}""")

                logger.info("   transaction started ------------------------------------BEGIN")

                rewardModel.save(
                        Reward.Builder()
                                .rewardId(UUID.randomUUID().toString())
                                .reviewId(eventInfo.reviewId)
                                .user(user)
                                .reason(subtract_reason)
                                .operation(subtract_operation)
                                .pointDelta(currPoint)
                                .build()
                )

                rewardModel.save(
                        Reward.Builder()
                                .rewardId(UUID.randomUUID().toString())
                                .reviewId(eventInfo.reviewId)
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
            logger.info("""    transaction started ------------------------------------BEGIN"""")
        }

        val currentReview = reviewModel.findReviewInfoByReviewId_native(eventInfo.reviewId)

        val currentPhotoIds = currentReview!!.photoIds.toList()
        val newPhotoIds = eventInfo.attachedPhotoIds.toList()

        val add_photo_ids: Array<String> = eventInfo.attachedPhotoIds.stream()
                .filter { photoId -> currentPhotoIds.contains(photoId) }
                .toList().toTypedArray()

        val delete_photo_ids: Array<String> = currentPhotoIds.stream()
                .filter { currentPhotoId -> !newPhotoIds.contains(currentPhotoId) }
                .toList().toTypedArray()

        val photoModel = eventDatabase.photoModel

        val review = reviewModel.findReviewByReviewId(eventInfo.reviewId)

        if (add_photo_ids.isNotEmpty()) {
            Arrays.stream(add_photo_ids)
                    .map { photoId -> Photo(photoId, review) }
                    .forEach(photoModel::save)
        }
        if (delete_photo_ids.isNotEmpty()) {
            Arrays.stream(delete_photo_ids)
                    .forEach(photoModel::remove)
        }

        logger.info("""    [✔︎] PHOTOS has been updated""")

        reviewModel.updateReview(
                eventInfo.reviewId,
                eventInfo.content
        )

        logger.info("""    [✔︎] REVIEWS review has been updated""")
        logger.info("""    transaction finished -------------------------------------END""")

        logger.info("===================================================================================END")
    }
}


