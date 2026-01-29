package com.mv1998.medronic.data.models

import com.mv1998.medronic.domain.models.Option
import com.mv1998.medronic.domain.models.Question
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id : Int,
    val title : String,
    val options : List<OptionDto>,
    val correctOptionId: Int,
    val isSelected : Boolean,
    val userSelectedOptionId: Int = -1
)

@Serializable
data class OptionDto(
    val id : Int,
    val title : String
)

fun QuestionDto.toDomain() : Question {
    return Question(
        id = id,
        title = title,
        options = options.map { it.toDomain() },
        correctOptionId = correctOptionId,
        isSelected = isSelected,
        userSelectedOptionId = userSelectedOptionId
    )
}

fun OptionDto.toDomain() : Option {
    return Option(
        id = id,
        title = title
    )
}