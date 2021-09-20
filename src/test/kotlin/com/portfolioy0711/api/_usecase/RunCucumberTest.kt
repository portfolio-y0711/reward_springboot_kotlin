package com.portfolioy0711.api._usecase

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
        strict = true,
        monochrome = true,
        glue = ["com.portfolioy0711.api._usecase"],
        stepNotifications = true,
        features = [
            "src/test/resources/features/ADD/scenarios.add.feature",
            "src/test/resources/features/MOD/scenarios.mod.feature",
            "src/test/resources/features/DEL/scenarios.del.feature"
        ],
        plugin = ["pretty"]
)

class RunCucumberTest
