package com.portfolioy0711.api.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.portfolioy0711.api.typings.EventDto
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import com.portfolioy0711.api.typings.exception.InvalidRequestException
import java.util.*

class EventValidator(val body: String) {
    val gson = Gson()

    val jsonLikeMap: Map<String, Any> by lazy {
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        gson.fromJson(body, object : TypeToken<Map<String, Any>>() {}.type)
    }

    fun validate(key: String, values: Array<String>): EventValidator {
        if (!jsonLikeMap.containsKey(key)) {
            throw InvalidRequestException("type must be one of the following values")
        } else {
            val type: String = jsonLikeMap.get(key) as String
            val isContains: Boolean = Arrays.stream(values).anyMatch { v -> v.equals(type) }

            if (!isContains) {
                throw InvalidRequestException(String.format("type must be one of %s but received %s", Arrays.toString(values), type))
            }
        }
        return this
    }
    fun <T> getValueAsType(key: String): T {
        return this.jsonLikeMap[key] as T
    }
//    fun <T extends EventDto> transform(type: Class<T>): T {
    inline fun <reified T> transform(java: Class<T>): T {
        return gson.fromJson(this.body, T::class.java)
    }
}
