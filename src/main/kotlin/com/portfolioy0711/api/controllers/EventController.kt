package com.portfolioy0711.api.controllers

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.portfolioy0711.api.services.EventService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import jdk.nashorn.internal.parser.JSONParser
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMethod.POST;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import springfox.documentation.service.MediaTypes


@RestController
@Api(tags = ["Event"])
class EventController(private val eventService: EventService) {

    @ApiOperation(value = "이벤트 처리")
    @RequestMapping(value = ["/events"], method = [POST])
    fun route(body: String) {
        val gson = Gson()
        val mapType = object: TypeToken<Map<String, Any>>() {}.type
        var map: Map<String, Any> = gson.fromJson(body, object: TypeToken<Map<String, Any>>() {}.type)
        println(map.keys)
    }
}
