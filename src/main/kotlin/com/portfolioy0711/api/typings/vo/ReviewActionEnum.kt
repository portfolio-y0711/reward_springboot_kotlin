package com.portfolioy0711.api.typings.vo

import java.util.stream.Stream
import kotlin.streams.asStream
import kotlin.streams.toList

enum class ReviewActionEnum(val actionType: String) {
    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");


    companion object {
        fun getReviewActionTypes(): Array<String> {
            return stream().map {
               e -> e.actionType
            }.toList().toTypedArray()
        }
        fun stream(): Stream<ReviewActionEnum> {
            return values().asSequence().asStream()
        }
    }

}
