package com.portfolioy0711.api._usecase

import com.portfolioy0711.api.ApiApplication
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@RunWith(Cucumber::class)
@CucumberOptions(
        strict = true,
        monochrome = true,
        glue = ["com.portfolioy0711.api._usecase.steps"],
        stepNotifications = true,
        features = ["src/test/resources/features/scenarios.add.feature"],
        plugin = ["pretty"]
)

class RunCucumberTest
