package com.portfolioy0711.api._i11

import com.portfolioy0711.api.data.entities.QUser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class ApiApplicationTest(private val em: EntityManager) {

    @BeforeEach
    internal fun setUp() { }

    @Test
    internal fun `querydsl smoking test`() {
        val query = JPAQueryFactory(em)
        val user = QUser.user;

        val users = query
                .select(user)
                .from(user)
                .fetch()

        println(users)
    }
}
