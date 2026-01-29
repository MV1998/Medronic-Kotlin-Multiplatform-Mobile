package com.mv1998.medronic.viewmodels.quiz_view_model

import com.mv1998.medronic.data.repository.QuizRepositoryImpl
import com.mv1998.medronic.domain.usecases.QuizUseCase

class QuizViewModelFactory {
    fun create() : QuizViewModel {
        val quizRepository = QuizRepositoryImpl()
        val quizUseCase = QuizUseCase(quizRepository)
        return QuizViewModel(quizUseCase)
    }
}