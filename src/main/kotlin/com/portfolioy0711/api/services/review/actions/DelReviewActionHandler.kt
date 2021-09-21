package com.portfolioy0711.api.services.review.actions

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.Reward
import com.portfolioy0711.api.services.review.ActionHandler
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.NoRecordException
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class DelReviewActionHandler(val eventDatabase: EventDatabase) : ActionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun handleEvent(event: Any) {
        val eventInfo = event as ReviewEventDto

        logger.info("""[EVENT: ReviewEventActionHandler (${eventInfo.action})] started process ========================START""")
        val reviewModel = eventDatabase.reviewModel

        val found = reviewModel.findReviewInfoByReviewId(eventInfo.reviewId)

        if (found == null) {
            logger.error("\t‣" + "\tno record exists by that reviewId")
            throw NoRecordException("no record exists by that reviewId")
        }

        val isRewarded = (found.rewarded == 1)

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

            val deductPoint = -latestRewardRecord.pointDelta
            logger.info("""    ‣ points to deduct: $deductPoint""")

            val userModel = eventDatabase.userModel
            val currPoint = userModel.findUserRewardPoint(eventInfo.userId)

            logger.info("""    user's current rewards point: $currPoint""")
            logger.info("""    user's next rewards point: ${currPoint + deductPoint}""")

            logger.info("""    transaction started ------------------------------------BEGIN""")

            val subtract_operation = "SUB"
            val subtract_reason = "DEL"

            val user = userModel.findUserByUserId(eventInfo.userId)

            rewardModel.save(
                    Reward.Builder()
                            .rewardId(UUID.randomUUID().toString())
                            .user(user)
                            .reviewId(eventInfo.reviewId)
                            .reason(subtract_reason)
                            .operation(subtract_operation)
                            .pointDelta(currPoint)
                            .build()
            )

            logger.info("""    [✔︎] REWARDS $subtract_operation record created""")

            userModel.updateRewardPoint(
                    eventInfo.userId,
                    currPoint + deductPoint
            )
            logger.info("""    [✔︎] USERS total rewardPoint updated""")
        }

        val currentReview = reviewModel.findReviewInfoByReviewId(eventInfo.reviewId)
        val delete_photo_ids: Array<String> = currentReview!!.photoIds.toList().toTypedArray()

        val photoModel = eventDatabase.photoModel

        Arrays.stream(delete_photo_ids)
                .forEach(photoModel::remove)

        logger.info("\t[✔︎] PHOTOS has been deleted");

        reviewModel.remove(eventInfo.reviewId)

        logger.info("\t[✔︎] REVIEWS review has been deleted");
        logger.info("\ttransaction finished -------------------------------------END");

        logger.info("===================================================================================END");
    }

}
