package com.mv1998.medronic.domain.usecases

import com.mv1998.medronic.domain.models.Question
import com.mv1998.medronic.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class QuizUseCase(private val quizRepository: QuizRepository) {
    fun execute() : Flow<List<Question>> {
        return quizRepository.getQuizList()
    }
}