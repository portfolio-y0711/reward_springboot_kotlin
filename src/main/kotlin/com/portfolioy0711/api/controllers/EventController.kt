package com.portfolioy0711.api.controllers

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

    @ApiOperation(value = "이벤트 처리")
    @RequestMapping(value = ["/events"], method = [POST])
    fun route(
            @RequestBody body: String
    ) {
        EventValidator(body).validate("type", EventTypeEnum.getEventTypes())
        this.eventService.route(body)

    }
}
