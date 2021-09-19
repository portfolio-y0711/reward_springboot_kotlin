package com.portfolioy0711.api._usecase.steps

import com.portfolioy0711.api.ApiApplication
import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.entities.Review
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
        classes = [ApiApplication::class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class ScenarioAddSteps: En {
    private val log = LoggerFactory.getLogger(this::class.java)

    init {

        DataTableType {
            entry: Map<String, String> ->
                Place(entry["placeId"]!!, entry["country"]!!, entry["name"]!!, entry["bonusPoint"]!!.toInt())
        }

        DataTableType {
            entry: Map<String, String> ->
                User(entry["userId"]!!, entry["name"]!!, entry["rewardPoint"]!!.toInt())
        }

        Given("1_아래와 같이 특정 장소가 등록되어 있음") { dataTable: DataTable ->
            val place: List<Place> = dataTable.asList(Place::class.java)
//            assertEquals(place, "")
        }

        And("2_아래와 같이 특정 유저가 등록되어 있음") { dataTable: DataTable ->
            val user: List<User> = dataTable.asList(User::class.java)
//            assertEquals(user, "")
        }

        Given("3_아래 장소에 대한 리뷰글이 존재하지 않음") { dataTable: DataTable ->
            val placeId = dataTable.row(1)
//            assertEquals(placeId, "")
        }

        Given("4_유저가 아래와 같이 리뷰글을 작성함") { dataTable: DataTable ->
            val review: Map<String, String> = dataTable.asMaps()[0]
            val type = review.get("type")!!
            val action = review.get("action")!!
            val reviewId = review.get("reviewId")!!
            val content = review.get("content")!!
            val attachedPhotoIds = (review.get("attachedPhotoIds") as String)!!.split(",")!!.toHashSet()
            val userId = review.get("userId")!!
            val placeId = review.get("placeId")!!

            val reviewEventDto = ReviewEventDto.Builder()
                    .reviewId(reviewId)
                    .content(content)
                    .type(type)
                    .action(action)
                    .attachedPhotoIds(attachedPhotoIds)
                    .placeId(placeId)
                    .userId(userId)
                    .build()

//            assertEquals(reviewEventDto, "")
        }

        Then("5_유저의 리워드 레코드가 아래와 같이 생성됨") { dataTable: DataTable ->
            val reward: Map<String, String> = dataTable.asMaps()[0]
            val userId = reward.get("userId")!!
            val reviewId = reward.get("reviewId")!!
            val operation = reward.get("operation")!!
            val pointDelta = reward.get("pointDelta")!!.toInt()
            val reason = reward.get("reason")

//            assertEquals(dataTable, "")
        }

        And("6_유저의 포인트 총점이 아래와 같아짐") { dataTable: DataTable ->
            val (userId, rewardPoint) = dataTable.row(1)
//            assertEquals(userId, "")
        }

        And("7_유저의 리뷰 레코드가 아래와 같이 생성됨") { dataTable: DataTable ->
            val review: Map<String, String> = dataTable.asMaps()[0]
            val reviewId = review.get("reviewId")!!
            val placeId = review.get("placeId")!!
            val content = review.get("content")!!
            val attachedPhotoIds = (review.get("attachedPhotoIds") as String).split(",").toHashSet()
            val userId = review.get("userId")!!
            val rewarded = review.get("rewarded")!!.toInt()

//            assertEquals(dataTable, "")
        }

    }

}
