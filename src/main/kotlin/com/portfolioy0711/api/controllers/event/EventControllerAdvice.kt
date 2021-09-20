package com.portfolioy0711.api.controllers.event

import com.portfolioy0711.api.typings.exception.DuplicateRecordException
import com.portfolioy0711.api.typings.exception.InvalidRequestException
import com.portfolioy0711.api.typings.exception.NoRecordException
import com.portfolioy0711.api.typings.response.ErrorMessage
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice(assignableTypes = [EventController::class])
class EventControllerAdvice {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [InvalidRequestException::class])
    fun invalidRequestException(ex: Exception, webRequest: WebRequest): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage.Builder()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timestamp(Date())
            .message(ex.message!!)
            .description(webRequest.getDescription(false))
            .build()
        logger.error("handled invalid request exception")
        return ResponseEntity<ErrorMessage>(message, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(value = [DuplicateRecordException::class])
    fun duplicateRecordException(ex: Exception, webRequest: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("handled duplicate record exception")
        val message = ErrorMessage.Builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(Date())
            .message(ex.message!!)
            .description(webRequest.getDescription(false))
            .build()
        return ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [NoRecordException::class])
    fun noRecordException(ex: Exception, webRequest: WebRequest): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage.Builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(Date())
            .message(ex.message!!)
            .description(webRequest.getDescription(false))
            .build()
        logger.error("handled no record exception")
        return ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST)
    }
}
