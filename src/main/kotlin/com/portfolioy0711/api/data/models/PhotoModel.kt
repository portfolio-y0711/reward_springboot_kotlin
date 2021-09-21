package com.portfolioy0711.api.data.models

import com.portfolioy0711.api.data.entities.Photo
import com.portfolioy0711.api.data.models.photo.PhotoCmdRepository
import com.portfolioy0711.api.data.models.photo.PhotoQueryRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PhotoModel(
        val photoCmdRepository: PhotoCmdRepository,
    val photoQueryRepository: PhotoQueryRepository
) {

    @Transactional
    fun save(photo: Photo): Photo {
       return photoCmdRepository.save(photo)
    }

    @Transactional
    fun remove(photoId: String) {
        return photoCmdRepository.deleteById(photoId)
    }
}
