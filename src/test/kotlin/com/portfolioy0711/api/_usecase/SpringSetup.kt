package com.portfolioy0711.api._usecase

import com.portfolioy0711.api.ApiApplication
import io.cucumber.java8.En
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
//@SpringBootTest(
//        classes = [ApiApplication::class],
//        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
//)
class SpringSetup : En {
    init {
//        Before { _ ->
//            InformationProcessorSimulator.start()
//        }
//
//        After { _ ->
//            InformationProcessorSimulator.stop()
//        }
//
//        LOGGER.info("Application started")
    }
}
