package com.portfolioy0711.api.data

import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    fun findUsers() = hashMapOf<String, String>(
            "uuid" to "3ede0ef2-92b7-4817-a5f3-0c575361f745",
            "name" to "michael"
    )
}
