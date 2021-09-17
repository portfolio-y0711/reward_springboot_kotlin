package com.portfolioy0711.api._i11._1_databases

import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.data.models.UserModel
import com.querydsl.jpa.impl.JPAQueryFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserModelTest {

    @Autowired
    lateinit var query: JPAQueryFactory

    @Autowired
    lateinit var userModel: UserModel

    @Test
    internal fun `userModel should save user entity`() {
        val user = QUser.user
        val userInstance = User.Builder()
                .userId("13ae28fe-8b79-4808-a102-b8ffd8d06098")
                .name("James")
                .rewardPoint(3)
                .build()
        userModel.save(userInstance)

        query.select(user)
            .from(user)
            .fetch()

    }
}
