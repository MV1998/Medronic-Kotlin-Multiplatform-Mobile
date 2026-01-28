package com.mv1998.medronic.fruit_list_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mv1998.medronic.viewmodels.fruit_view_model.FruitViewModel
import com.mv1998.medronic.viewmodels.fruit_view_model.FruitViewModelFactory

class FruitViewModelAndroidFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FruitViewModel::class.java)) {
            return FruitViewModelFactory().create() as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}