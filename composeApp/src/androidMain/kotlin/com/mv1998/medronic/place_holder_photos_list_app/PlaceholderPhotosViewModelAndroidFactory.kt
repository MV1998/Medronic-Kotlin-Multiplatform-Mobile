package com.mv1998.medronic.place_holder_photos_list_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mv1998.medronic.viewmodels.place_holder_photos_view_model.PlaceholderPhotosViewModel
import com.mv1998.medronic.viewmodels.place_holder_photos_view_model.PlaceholderPhotosViewModelFactory

class PlaceholderPhotosViewModelAndroidFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceholderPhotosViewModel::class.java)) {
            return PlaceholderPhotosViewModelFactory().create() as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}