package com.portfolioy0711.api._i11

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    fun `GET_users_should return List of User`() {
        mockMvc.get("/users")
                .andDo { print() }
                .andExpect {
                    status {
                        isOk()
                    }
                }
    }
}
