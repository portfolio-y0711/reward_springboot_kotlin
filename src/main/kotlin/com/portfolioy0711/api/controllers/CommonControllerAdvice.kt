package com.portfolioy0711.api.controllers

import com.portfolioy0711.api.typings.exception.EntityNotFoundException
import com.portfolioy0711.api.typings.response.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class CommonControllerAdvice {

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun entityNotFoundException(ex: Exception, webRequest: WebRequest): ResponseEntity<ErrorMessage> {
        val message: ErrorMessage = ErrorMessage.Builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Date())
                .message(ex.message!!)
                .description(webRequest.getDescription(false))
                .build()
        return ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND)
    }
}
