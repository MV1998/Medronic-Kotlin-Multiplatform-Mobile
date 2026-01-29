package com.mv1998.medronic.domain.repository

import com.mv1998.medronic.data.models.QuestionDto
import com.mv1998.medronic.domain.models.Question
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getQuizList() : Flow<List<Question>>
}