package com.portfolioy0711.api.data.seeder

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.util.*
import kotlin.streams.toList

@Component
class DataSeeder: CommandLineRunner {

    @Autowired
    lateinit var env: Environment

    @Autowired
    lateinit var eventDatabase: EventDatabase

    fun seedUsers() {
        val resource = ClassPathResource("/seeds/users.json").file
        val json = String(Files.readAllBytes(resource.toPath()))
        val objectMapper = ObjectMapper()
        val users: List<User> = objectMapper.readValue(json)
        val userModel = eventDatabase.userModel;
        users.forEach { user -> userModel.save(user) }
    }

    fun seedPlaces() {
        val resource = ClassPathResource("/seeds/places.json").file
        val json = String(Files.readAllBytes(resource.toPath()))
        val objectMapper = ObjectMapper()
        val places: List<Place> = objectMapper.readValue(json)
        val placeModel = eventDatabase.placeModel;
        places.forEach { place -> placeModel.save(place) }
    }

    override fun run(vararg args: String?) {
        val isProduction: Boolean = Arrays.stream(env.activeProfiles).toList().contains("production") || Arrays.stream(args).toList().contains("production")

        if (isProduction) {
            seedUsers()
            seedPlaces()
        }
    }

}

