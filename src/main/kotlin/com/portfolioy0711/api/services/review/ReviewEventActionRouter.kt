package com.portfolioy0711.api.services.review

import com.portfolioy0711.api.services.EventRouter
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.vo.ReviewActionEnum
import com.portfolioy0711.api.util.EventValidator
import org.springframework.stereotype.Component

interface ActionHandler {
    fun handleEvent(event: Any): Unit
}

@Component
class ReviewEventActionRouter : EventRouter {
    val routes: MutableMap<String, ActionHandler> = mutableMapOf()

    fun addRoute(path: String, handler: ActionHandler): ReviewEventActionRouter {
        this.routes[path] = handler
        return this
    }
    override fun route(body: String) {
        val eventInfo: ReviewEventDto = EventValidator(body)
                .validate("action", ReviewActionEnum.getReviewActionTypes())
                .transform<ReviewEventDto>(ReviewEventDto::class.java)
        this.routes[eventInfo.action]!!.handleEvent(eventInfo)

    }
}
