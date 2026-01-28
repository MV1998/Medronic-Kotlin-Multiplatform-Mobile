package com.mv1998.medronic.viewmodels.fruit_view_model

import com.mv1998.medronic.data.repository.FruitRepositoryImpl
import com.mv1998.medronic.domain.usecases.SearchFruitUseCase

class FruitViewModelFactory {
    fun create() : FruitViewModel {
        val repository = FruitRepositoryImpl()
        val fruitUseCase = SearchFruitUseCase(repository)
        return FruitViewModel(fruitUseCase)
    }
}