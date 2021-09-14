package com.portfolioy0711.api._unit._1_controllers

import com.ninjasquad.springmockk.MockkBean
import com.portfolioy0711.api.controllers.UserController
import com.portfolioy0711.api.data.UserRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserControllerTest {
    lateinit var userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
        userRepository = mockk<UserRepository>(relaxed = true)
    }

    @Test
    fun `GET_api_users should invoke service_fetch_users`() {
        val controller = UserController(userRepository)
        controller.users()
        verify(exactly = 1) {
           userRepository.findUsers()
        }

    }

}
