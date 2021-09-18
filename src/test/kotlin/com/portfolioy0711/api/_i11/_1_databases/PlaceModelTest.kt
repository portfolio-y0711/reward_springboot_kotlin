package com.portfolioy0711.api._i11._1_databases

import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.entities.QPlace
import com.portfolioy0711.api.data.models.PlaceModel
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class PlaceModelTest {

    @Autowired
    lateinit var query: JPAQueryFactory

    @Autowired
    lateinit var cmd: PlaceCmdRepository

    @Autowired
    lateinit var placeModel: PlaceModel

    @Test
    @Transactional
    internal fun `placeModel_findPlaceByPlaceId(placeId)`() {
        val expected = Place.Builder()
            .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
            .country("호주")
            .name("멜번")
            .bonusPoint(0)
            .build()

        cmd.save(expected)

        val place = QPlace.place
        val placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        val actual = query.select(place)
                .from(place)
                .where(place.placeId.eq(placeId))
                .fetchOne()!!

        assertEquals(expected, actual)
    }
}
