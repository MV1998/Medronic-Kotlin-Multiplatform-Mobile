package com.mv1998.medronic.data.repository

import com.mv1998.medronic.data.models.toDomain
import com.mv1998.medronic.data.network.PhotoClient
import com.mv1998.medronic.domain.models.Photo
import com.mv1998.medronic.domain.repository.PlaceholderPhotosListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlaceholderPhotosListRepositoryImpl(private val photoClient: PhotoClient,
                                          private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    PlaceholderPhotosListRepository {

    override fun getPhotosList(): Flow<List<Photo>> {
        return flow {
            emit(photoClient.getPhotos().map { it.toDomain() })
        }.flowOn(dispatcher)
    }

}