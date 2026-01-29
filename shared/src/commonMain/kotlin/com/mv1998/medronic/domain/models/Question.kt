package com.mv1998.medronic.domain.models

data class Question(
    val id : Int,
    val title : String,
    val options : List<Option>,
    val correctOptionId: Int,
    val isSelected : Boolean,
    val userSelectedOptionId: Int = -1
) {
    val isAnswered : Boolean
        get() =  userSelectedOptionId != -1

    val isCorrect : Boolean
        get() = userSelectedOptionId == correctOptionId
}

data class Option(
    val id : Int,
    val title : String
)
