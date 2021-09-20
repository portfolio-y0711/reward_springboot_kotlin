package com.portfolioy0711.api.controllers.event

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.services.EventService
import com.portfolioy0711.api.services.review.ReviewEventActionRouter
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMethod.POST;
import com.portfolioy0711.api.typings.vo.EventTypeEnum
import com.portfolioy0711.api.util.EventValidator
import io.swagger.annotations.*
import org.springframework.web.bind.annotation.RequestBody

@RestController
@Api(tags = ["Event"])
class EventController(private val eventService: EventService, private val eventDatabase: EventDatabase) {

    init {
        val reviewEventRouter = ReviewEventActionRouter()
        reviewEventRouter
                .addRoute("ADD", AddReviewActionHandler(eventDatabase))

        this.eventService
                .addEventRouter("REVIEW", reviewEventRouter)
    }

    @ApiImplicitParams(
            ApiImplicitParam(name = "body", dataTypeClass = String::class, paramType = "body", example =
"""{ 
  "type": "REVIEW",
  "action": "ADD",
  "reviewId": "240a0658-dc5f-4878-9831-ebb7b26687772",
  "content": "좋아요",
  "attachedPhotoIds": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f" 
}""" ))
    @RequestMapping(value = ["/events"], method = [POST])
    fun route(
            @RequestBody body: String
    ) {
        EventValidator(body).validate("type", EventTypeEnum.getEventTypes())
        this.eventService.route(body)
    }
}
