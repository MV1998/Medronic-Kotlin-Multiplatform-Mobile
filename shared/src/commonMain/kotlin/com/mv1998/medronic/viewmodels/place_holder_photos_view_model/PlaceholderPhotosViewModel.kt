package com.mv1998.medronic.viewmodels.place_holder_photos_view_model

import com.mv1998.medronic.domain.models.Photo
import com.mv1998.medronic.domain.usecases.PlaceholderPhotosListUseCase
import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val isLoading : Boolean = true,
    val photos : List<Photo>
)

class PlaceholderPhotosViewModel(
    private val placeholderPhotosListUseCase: PlaceholderPhotosListUseCase
): ViewModel() {
    private val _photosResult : CMutableStateFlow<UiState> = MutableStateFlow(UiState(isLoading = true, photos = emptyList())).cMutableStateFlow()
    val photosResult : CStateFlow<UiState> = _photosResult.cStateFlow()

    init {
        viewModelScope.launch {
            placeholderPhotosListUseCase.invoke()
                .collect { photos ->
                    _photosResult.update { state ->
                        state.copy(
                            isLoading = false,
                            photos
                        )
                    }
                }
        }
    }

}