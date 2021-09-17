package com.portfolioy0711.api.services.review.actions

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.services.review.ActionHandler
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.DuplicateRecordException
import org.slf4j.LoggerFactory

class AddReviewActionHandler(val eventDatabase: EventDatabase) : ActionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handleEvent(event: Any) {
        val eventInfo = event as ReviewEventDto
        val reviewModel = eventDatabase.reviewModel
        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.action));

//        val isDuplicate: Boolean = reviewModel.checkRecordExistsByReviewId(eventInfo.reviewId)


//        if (isDuplicate) {
//            logger.error("""    ‣   process terminated: due to context error""");
//            throw DuplicateRecordException("duplicate record exist by that reviewId")
//        }

//        logger.error("\t‣" + "\tprocess terminated: due to context error");


    }
}
