package com.portfolioy0711.api.services

import com.portfolioy0711.api.typings.vo.EventTypeEnum
import com.portfolioy0711.api.util.EventValidator
import org.springframework.stereotype.Service

interface EventRouter {
    fun route(event: String): Unit
}

@Service
class EventService() {
   private val routes: MutableMap<String, EventRouter> = mutableMapOf()

    fun addEventRouter(path: String, router: EventRouter): EventService {
        this.routes.put(path, router)
        return this
    }

    fun route(body: String) {
       val eventType: String = EventValidator(body).validate("type", EventTypeEnum.getEventTypes())
               .getValueAsType<String>("type")
        this.routes.get(eventType)!!.route(eventType)

    }
}
