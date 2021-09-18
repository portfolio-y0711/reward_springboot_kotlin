package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Photo
import com.portfolioy0711.api.data.models.photo.PhotoCmdRepository
import com.portfolioy0711.api.data.models.photo.PhotoQueryRepository
import org.springframework.stereotype.Component

@Component
class PhotoModel(
        val photoCmdRepository: PhotoCmdRepository,
    val photoQueryRepository: PhotoQueryRepository
) {

    fun save(photo: Photo): Photo {
       return photoCmdRepository.save(photo)
    }
}
