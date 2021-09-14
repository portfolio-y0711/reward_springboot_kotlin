package com.portfolioy0711.api._unit._2_services

import com.portfolioy0711.api.data.UserRepository
import com.portfolioy0711.api.services.UserService
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserServiceTest {
    lateinit var userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
        userRepository = mockk<UserRepository>(relaxed = true)
    }

    @Test
    fun `SERVICE_fetch_users should invoke REPOSITORY_findUsers`() {
        val service = UserService(userRepository)
        service.fetchUsers()
        verify(exactly = 1) {
           userRepository.findUsers()
        }
    }
}
