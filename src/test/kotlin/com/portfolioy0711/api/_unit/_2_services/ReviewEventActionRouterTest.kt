package com.portfolioy0711.api._unit._2_services

import com.portfolioy0711.api.services.review.ReviewEventActionRouter
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReviewEventActionRouterTest {

    @Test
    internal fun `reviewEventActionRouter should route`() {
        val addReviewActionHandler = mockk<AddReviewActionHandler>()
        every { addReviewActionHandler.handleEvent(any()) } returns Unit

        val reviewEventActionRouter = ReviewEventActionRouter()
        reviewEventActionRouter.addRoute("ADD", addReviewActionHandler)
        reviewEventActionRouter.route("""{
            "type": "REVIEW",
            "action": "ADD",
            "reviewId": "240a0658-dc5f-4878-9831-ebb7b26687772",
            "content": "좋아요",
            "attachedPhotoIds": [
            "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
            "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
            ],
            "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
            "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        }""")


        verify(exactly = 1) {
            addReviewActionHandler.handleEvent(any())
        }

    }
}