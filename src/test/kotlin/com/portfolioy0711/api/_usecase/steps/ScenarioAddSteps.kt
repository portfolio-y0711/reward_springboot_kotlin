package com.portfolioy0711.api._usecase.steps

import com.portfolioy0711.api.data.EventDatabase
import com.portfolioy0711.api.data.entities.Place
import com.portfolioy0711.api.data.entities.User
import com.portfolioy0711.api.data.models.PlaceModel
import com.portfolioy0711.api.data.models.ReviewModel
import com.portfolioy0711.api.data.models.RewardModel
import com.portfolioy0711.api.data.models.UserModel
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler
import com.portfolioy0711.api.typings.dto.ReviewEventDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import org.junit.jupiter.api.Assertions.assertEquals
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


class ScenarioAddSteps: En {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var placeModel: PlaceModel

    @Autowired
    lateinit var userModel: UserModel

    @Autowired
    lateinit var reviewModel: ReviewModel

    @Autowired
    lateinit var rewardModel: RewardModel

    @Autowired
    lateinit var eventDatabase: EventDatabase

    init {

        Before { _: Scenario ->
            val user = User.Builder()
                    .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                    .name("Michael")
                    .rewardPoint(0)
                    .build()
            userModel.save(user)

            val place = Place.Builder()
                    .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                    .country("호주")
                    .name("멜번")
                    .bonusPoint(1)
                    .build()
            placeModel.save(place)
        }

        DataTableType {
            entry: Map<String, String> ->
                Place(entry["placeId"]!!, entry["name"]!!, entry["country"]!!, entry["bonusPoint"]!!.toInt())
        }

        DataTableType {
            entry: Map<String, String> ->
                User(entry["userId"]!!, entry["name"]!!, entry["rewardPoint"]!!.toInt())
        }

        Given("ADD_1_아래와 같이 특정 장소가 등록되어 있음") { dataTable: DataTable ->
            val place: List<Place> = dataTable.asList(Place::class.java)
            val expected = place.get(0)
            val actual = placeModel.findPlaceByPlaceId(place.get(0).placeId)
            assertEquals(expected, actual)
        }

        And("ADD_2_아래와 같이 특정 유저가 등록되어 있음") { dataTable: DataTable ->
            val user: List<User> = dataTable.asList(User::class.java)
            val expected = user.get(0)
            val actual = userModel.findUserByUserId(expected.userId)
            assertEquals(expected, actual)
        }

        Given("ADD_3_아래 장소에 대한 리뷰글이 존재하지 않음") { dataTable: DataTable ->
            val placeId = dataTable.row(1).get(0)
            val reviewCounts = reviewModel.findReviewCountsByPlaceId(placeId)
            assertEquals(reviewCounts, 0)
        }

        When("ADD_4_유저가 아래와 같이 리뷰글을 작성함") { dataTable: DataTable ->
            val review: Map<String, String> = dataTable.asMaps()[0]
            val type = review.get("type")!!
            val action = review.get("action")!!
            val reviewId = review.get("reviewId")!!
            val content = review.get("content")!!
            val attachedPhotoIds = (review.get("attachedPhotoIds") as String)!!.split(",").toHashSet()
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

            AddReviewActionHandler(eventDatabase).handleEvent(reviewEventDto)
        }

        Then("ADD_5_유저의 리워드 레코드가 아래와 같이 생성됨") { dataTable: DataTable ->
            val reward: Map<String, String> = dataTable.asMaps()[0]
            val userId = reward.get("userId")!!
            val reviewId = reward.get("reviewId")!!
            val operation = reward.get("operation")!!
            val pointDelta = reward.get("pointDelta")!!.toInt()
            val reason = reward.get("reason")!!

            val actual = rewardModel.findLatestUserReviewRewardByReviewId(userId, reviewId)

            assertEquals(actual.user.userId, userId)
            assertEquals(actual.reviewId, reviewId)
            assertEquals(actual.operation, operation)
            assertEquals(actual.pointDelta, pointDelta)
            assertEquals(actual.reason, reason)
        }

        And("ADD_6_유저의 포인트 총점이 아래와 같아짐") { dataTable: DataTable ->
            val (userId, rewardPoint) = dataTable.row(1)
            val actual = userModel.findUserRewardPoint(userId)
            assertEquals(rewardPoint.toInt(), actual)
        }

        And("ADD_7_유저의 리뷰 레코드가 아래와 같이 생성됨") { dataTable: DataTable ->
            val review: Map<String, String> = dataTable.asMaps()[0]
            val reviewId = review.get("reviewId")!!
            val placeId = review.get("placeId")!!
            val content = review.get("content")!!
//            val attachedPhotoIds = (review.get("attachedPhotoIds") as String).split(",").toHashSet()
            val userId = review.get("userId")!!
            val rewarded = review.get("rewarded")!!.toInt()

            val actual = reviewModel.findReviewByReviewId(reviewId)!!

            assertEquals(actual.reviewId, reviewId)
            assertEquals(actual.place.placeId, placeId)
            assertEquals(actual.content, content)
//            assertEquals(actual.photos, attachedPhotoIds)
            assertEquals(actual.user.userId, userId)
            assertEquals(actual.rewarded, rewarded)
        }

    }

}
