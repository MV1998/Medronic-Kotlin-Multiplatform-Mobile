package com.mv1998.medronic.viewmodels.place_holder_photos_view_model

import com.mv1998.medronic.core.AppHttpClient
import com.mv1998.medronic.data.network.PhotoClient
import com.mv1998.medronic.data.repository.PlaceholderPhotosListRepositoryImpl
import com.mv1998.medronic.domain.usecases.PlaceholderPhotosListUseCase

class PlaceholderPhotosViewModelFactory {
    fun create() : PlaceholderPhotosViewModel {
        val photoClient = PhotoClient(AppHttpClient.client)
        val placeholderPhotosListRepository = PlaceholderPhotosListRepositoryImpl(photoClient)
        val placeholderPhotosListUseCase = PlaceholderPhotosListUseCase(placeholderPhotosListRepository)
        return PlaceholderPhotosViewModel(placeholderPhotosListUseCase)
    }
}