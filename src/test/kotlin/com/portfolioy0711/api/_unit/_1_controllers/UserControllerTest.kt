package com.portfolioy0711.api._unit._1_controllers

import com.portfolioy0711.api.controllers.UserController
import com.portfolioy0711.api.services.UserService
//import io.mockk.mockk
//import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserControllerTest {
    lateinit var userService: UserService

    @BeforeEach
    internal fun setUp() {
//        userService = mockk<UserService>(relaxed = true)
    }

    @Test
    fun `GET_users should invoke service_fetch_users`() {
//        val controller = UserController(userService)
//        controller.getUsers()
//        verify(exactly = 1) {
//           userService.fetchUsers()
//        }
    }


}
