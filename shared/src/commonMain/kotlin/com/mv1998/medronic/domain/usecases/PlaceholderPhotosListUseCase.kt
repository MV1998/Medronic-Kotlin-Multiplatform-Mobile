package com.mv1998.medronic.domain.usecases

import com.mv1998.medronic.domain.models.Photo
import com.mv1998.medronic.domain.repository.PlaceholderPhotosListRepository
import kotlinx.coroutines.flow.Flow

class PlaceholderPhotosListUseCase(private val placeholderPhotosListRepository: PlaceholderPhotosListRepository) {
    fun invoke() : Flow<List<Photo>> {
        return placeholderPhotosListRepository.getPhotosList()
    }
}