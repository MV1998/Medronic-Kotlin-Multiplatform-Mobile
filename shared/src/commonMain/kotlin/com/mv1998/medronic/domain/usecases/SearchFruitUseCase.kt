package com.mv1998.medronic.domain.usecases

import com.mv1998.medronic.domain.repository.FruitRepository
import kotlinx.coroutines.flow.Flow

class SearchFruitUseCase(
    private val repository: FruitRepository
) {
    fun execute() : Flow<List<String>> = repository.getFruit()
}