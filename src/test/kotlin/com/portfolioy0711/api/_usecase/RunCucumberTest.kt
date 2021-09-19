package com.portfolioy0711.api._usecase

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

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
