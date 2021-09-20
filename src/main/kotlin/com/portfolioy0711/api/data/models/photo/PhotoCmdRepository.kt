package com.portfolioy0711.api.data.models.photo

import com.portfolioy0711.api.data.entities.Photo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhotoCmdRepository: JpaRepository<Photo, String> {
}
