package com.mv1998.medronic.viewmodels.quiz_view_model

import com.mv1998.medronic.domain.models.Question
import com.mv1998.medronic.domain.usecases.QuizUseCase
import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val isLoading : Boolean = true,
    val quizList : List<Question> = emptyList<Question>(),
    val currentQuestionIndex : Int = 0,
    val totalCorrectCount : Int = 0,
    val totalWrongCount : Int = 0,
    val isLastQuestion : Boolean = false,
    val correctAnswer : Int = 0,
    val userSelected : Int = -1
) {
    val currentQuestion : Question? = quizList.getOrNull(currentQuestionIndex)
}

class QuizViewModel(private val quizUseCase: QuizUseCase) : ViewModel() {

    private val _quizState : CMutableStateFlow<QuizUiState> = MutableStateFlow(
        QuizUiState()).cMutableStateFlow()

    val quizState : CStateFlow<QuizUiState> = _quizState.cStateFlow()

    init {
        loadQuiz()
    }

    private fun loadQuiz() {
        viewModelScope.launch {
            quizUseCase.execute().collect { list ->
                _quizState.update { state ->
                    state.copy(
                        isLoading = false,
                        quizList = list
                    )
                }
            }
        }
    }

    fun onNext() {
        _quizState.update { state ->
            val nextIndex = _quizState.value.currentQuestionIndex + 1
            if (nextIndex < _quizState.value.quizList.size) {
                state.copy(
                    currentQuestionIndex = nextIndex
                )
            } else {
                state.copy(
                    isLastQuestion = true
                )
            }
        }
    }

    fun onQuestionSelect(selectedOptionId : Int) {
        println("$selectedOptionId")
        _quizState.update { state ->
            val updatedQuizList = state.quizList.map { question ->
                if (question.id - 1 == state.currentQuestionIndex && question.userSelectedOptionId == -1) {
                    question.copy(userSelectedOptionId = selectedOptionId)
                }else {
                    question
                }
            }
            val currentQuestion = updatedQuizList.first { it.id - 1 == state.currentQuestionIndex }
            val correctCount = updatedQuizList.count { it.isAnswered && it.isCorrect }
            val wrongCount = updatedQuizList.count { it.isAnswered && !it.isCorrect }
            state.copy(
                quizList = updatedQuizList,
                userSelected = currentQuestion.userSelectedOptionId ?: -1,
                correctAnswer = currentQuestion.correctOptionId,
                totalCorrectCount = correctCount,
                totalWrongCount = wrongCount
            )
        }
    }
}