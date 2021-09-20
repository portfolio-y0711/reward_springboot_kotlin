package com.portfolioy0711.api._usecase

import com.portfolioy0711.api.ApiApplication
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@SpringBootTest(
        classes = [ApiApplication::class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class SpringSetup : En {
    private val log = LoggerFactory.getLogger(SpringSetup::class.java)

    init {

//        Before { scenario: Scenario ->
        Before { _ ->
//            CommonBackground.processed()
        }

        After { scenario: Scenario ->
            log.info("Before scenario : " + scenario.name)
        }

    }
}
