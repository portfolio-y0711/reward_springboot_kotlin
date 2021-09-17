package com.portfolioy0711.api.controllers

import com.portfolioy0711.api.services.EventService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMethod.POST;
import com.portfolioy0711.api.typings.vo.EventTypeEnum
import com.portfolioy0711.api.util.EventValidator
import io.swagger.annotations.*
import org.springframework.web.bind.annotation.RequestBody

@RestController
@Api(tags = ["Event"])
class EventController(private val eventService: EventService) {

    @ApiOperation(value = "이벤트 처리")
    @RequestMapping(value = ["/events"], method = [POST])
    fun route(
            @RequestBody body: String
    ) {
        EventValidator(body).validate("type", EventTypeEnum.getEventTypes())
        this.eventService.route(body)

    }
}
