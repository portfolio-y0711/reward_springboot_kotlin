package com.portfolioy0711.api.data.seeder

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.portfolioy0711.api.data.UserRepository
import com.portfolioy0711.api.data.entities.User
import jdk.internal.org.objectweb.asm.TypeReference
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files

@Component
class DataSeeder: CommandLineRunner {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun run(vararg args: String?) {
        val resource = ClassPathResource("/seeds/users.json").file
        val json = String(Files.readAllBytes(resource.toPath()))
        val objectMapper = ObjectMapper()
        val users: List<User> = objectMapper.readValue(json)
        users.forEach { user -> userRepository.save(user) }
    }
}

