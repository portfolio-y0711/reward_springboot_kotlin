package com.portfolioy0711.api._i11._1_databases

import com.portfolioy0711.api.data.entities.QUser
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.data.models.UserModel
import com.querydsl.jpa.impl.JPAQueryFactory
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.FlushModeType
import kotlin.math.exp

@SpringBootTest
class UserModelTest {

    @Autowired
    lateinit var query: JPAQueryFactory

    @Autowired
    lateinit var userModel: UserModel

    @AfterEach
    fun tearDown() {
        userModel.deleteAll()
    }

    @Test
    fun `userModel_save`() {
        val user = QUser.user
        val expected = User.Builder()
                .userId("13ae28fe-8b79-4808-a102-b8ffd8d06098")
                .name("James")
                .rewardPoint(3)
                .build()
        userModel.save(expected)

        val actual = query.select(user)
            .from(user)
            .fetchOne()

        assertEquals(expected, actual)
    }

    @Test
    fun `userModel_findUserById`() {
        val user = QUser.user
        val userId = "13ae28fe-8b79-4808-a102-b8ffd8d06098"
        val expected = User.Builder()
                .userId(userId)
                .name("James")
                .rewardPoint(3)
                .build()

        userModel.save(expected)

        val actual = query.select(user)
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne()!!

        assertEquals(expected, actual)
    }

    @Test
    fun `userModel_updateRewardPoint`() {
        val user = QUser.user
        val userId = "13ae28fe-8b79-4808-a102-b8ffd8d06098"
        val expected = User.Builder()
                .userId(userId)
                .name("James")
                .rewardPoint(3)
                .build()

        userModel.save(expected)

        userModel.updateRewardPoint(userId, 2)

        val actual = query.select(user)
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne()!!

        assertEquals(expected.rewardPoint - 1, actual.rewardPoint)
    }
}
