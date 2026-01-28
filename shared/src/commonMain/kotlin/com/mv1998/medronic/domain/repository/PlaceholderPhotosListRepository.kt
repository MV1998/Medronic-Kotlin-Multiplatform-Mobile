package com.mv1998.medronic.domain.repository

import com.mv1998.medronic.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface PlaceholderPhotosListRepository{
    fun getPhotosList() : Flow<List<Photo>>
}