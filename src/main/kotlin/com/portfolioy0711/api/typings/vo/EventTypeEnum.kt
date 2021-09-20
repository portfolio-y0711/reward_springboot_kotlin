package com.portfolioy0711.api.typings.vo

import java.util.stream.Stream
import kotlin.streams.asStream
import kotlin.streams.toList

enum class EventTypeEnum(val eventType: String) {
    REVIEW("REVIEW"),
    BLAR_BLAR("BLAR_BLAR");

    companion object {
        fun getEventTypes(): Array<String> {
            return stream().map{
                e -> e.eventType as String
            }.toList().toTypedArray()
        }
        fun stream(): Stream<EventTypeEnum> {
            return values().asSequence().asStream()
        }
    }
}
