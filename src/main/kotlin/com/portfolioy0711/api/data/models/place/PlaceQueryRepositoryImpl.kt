package com.portfolioy0711.api.data.models.place

import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.entities.QPlace
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class PlaceQueryRepositoryImpl: PlaceQueryRepository {
    @Autowired
    lateinit var query: JPAQueryFactory

    override fun findPlaceByPlaceId(placeId: String): Place {
        val place = QPlace.place
        return query.select(place)
                .from(place)
                .where(place.placeId.eq(placeId))
                .fetchOne()!!
    }
}
