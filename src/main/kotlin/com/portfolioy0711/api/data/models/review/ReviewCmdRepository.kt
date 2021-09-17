package com.portfolioy0711.api.data.models.review

import com.portfolioy0711.api.data.entities.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewCmdRepository: JpaRepository<Review, String> { }
