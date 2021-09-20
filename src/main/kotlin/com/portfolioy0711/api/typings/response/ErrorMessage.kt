package com.portfolioy0711.api.typings.response

import java.util.*
import kotlin.properties.Delegates

data class ErrorMessage(
    val statusCode: Int,
    val timestamp: Date,
    val message: String,
    val description: String
) {
    private constructor(builder: Builder): this(builder.statusCode, builder.timestamp, builder.message, builder.description)

    class Builder {
        var statusCode by Delegates.notNull<Int>()
        lateinit var timestamp: Date
        lateinit var message: String
        lateinit var description: String

        fun statusCode(statusCode: Int) = apply{
            this.statusCode = statusCode
        }
        fun timestamp(timestamp: Date) = apply {
            this.timestamp = timestamp
        }
        fun message(message: String) = apply {
            this.message = message
        }
        fun description(description: String) = apply {
            this.description = description
        }

        fun build() = ErrorMessage(statusCode, timestamp, message, description)

    }
}
