package com.mv1998.medronic.domain.repository

import kotlinx.coroutines.flow.Flow

interface FruitRepository {
    fun getFruit() : Flow<List<String>>
}