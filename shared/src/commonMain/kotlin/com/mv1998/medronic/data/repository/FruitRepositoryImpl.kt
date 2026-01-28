package com.mv1998.medronic.data.repository

import com.mv1998.medronic.domain.repository.FruitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FruitRepositoryImpl : FruitRepository {
    override fun getFruit(): Flow<List<String>> {
        return flow {
            delay(3000)
            emit(
                listOf(
                    "Apple",
                    "Apricot",
                    "Avocado",
                    "Banana",
                    "Blackberry",
                    "Blueberry",
                    "Cantaloupe",
                    "Cherry",
                    "Coconut",
                    "Cranberry",
                    "Date",
                    "Dragonfruit",
                    "Durian",
                    "Fig",
                    "Grape",
                    "Grapefruit",
                    "Guava",
                    "Kiwi",
                    "Lemon",
                    "Lime",
                    "Lychee",
                    "Mango",
                    "Melon",
                    "Mulberry",
                    "Nectarine",
                    "Orange",
                    "Papaya",
                    "Passionfruit",
                    "Peach",
                    "Pear",
                    "Pineapple",
                    "Plum",
                    "Pomegranate",
                    "Raspberry",
                    "Strawberry",
                    "Tangerine",
                    "Watermelon"
                )
            )
        }
    }
}