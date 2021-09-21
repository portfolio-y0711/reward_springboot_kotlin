package com.portfolioy0711.api.controllers.user

import com.portfolioy0711.api.services.UserService
import com.portfolioy0711.api.typings.response.UserRewardPointResponse
import com.portfolioy0711.api.typings.response.UserRewardResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@Api(tags = ["User"])
class UserController(private val userService: UserService) {

    @RequestMapping(value = ["/users/{userId}/rewardPoint"], method = [GET])
    fun getUserRewardPoint(
        @ApiParam(
            value = "The user id",
            required = true,
            example = "3ede0ef2-92b7-4817-a5f3-0c575361f745"
        )
        @PathVariable userId: String
    ): UserRewardPointResponse {
        val userRewardPoint = userService.fetchUserRewardPoint(userId)
        return UserRewardPointResponse(userRewardPoint)
    }

    @RequestMapping(value = ["/users/{userId}/rewards"], method = [GET])
    fun getUserRewardHistory(
        @ApiParam(
            value = "The user id",
            required = true,
            example = "3ede0ef2-92b7-4817-a5f3-0c575361f745"
        )
        @PathVariable userId: String
    ): MutableList<UserRewardResponse>? {
       return userService.fetchUserRewards(userId)
    }


}
