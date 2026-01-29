package com.mv1998.medronic.data.repository

import com.mv1998.medronic.data.models.OptionDto
import com.mv1998.medronic.data.models.QuestionDto
import com.mv1998.medronic.data.models.toDomain
import com.mv1998.medronic.domain.models.Question
import com.mv1998.medronic.domain.repository.QuizRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuizRepositoryImpl : QuizRepository {
    override fun getQuizList(): Flow<List<Question>> {
        val questions = listOf(
            // ---------- KMM QUESTIONS ----------
            QuestionDto(
                id = 1,
                title = "What does KMM stand for?",
                options = listOf(
                    OptionDto(1, "Kotlin Multiplatform Mobile"),
                    OptionDto(2, "Kotlin Mobile Module"),
                    OptionDto(3, "Kotlin Managed Memory"),
                    OptionDto(4, "Kotlin Modern Model")
                ),
                correctOptionId = 1,
                isSelected = false
            ),

            QuestionDto(
                id = 2,
                title = "Which languages can be shared using KMM?",
                options = listOf(
                    OptionDto(1, "Kotlin only"),
                    OptionDto(2, "Kotlin and Java"),
                    OptionDto(3, "Kotlin for Android and iOS"),
                    OptionDto(4, "Java and Swift")
                ),
                correctOptionId = 3,
                isSelected = false
            ),

            QuestionDto(
                id = 3,
                title = "Which layer is usually shared in KMM?",
                options = listOf(
                    OptionDto(1, "UI layer"),
                    OptionDto(2, "Business logic layer"),
                    OptionDto(3, "Platform UI"),
                    OptionDto(4, "View layer only")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 4,
                title = "Which iOS language works with KMM?",
                options = listOf(
                    OptionDto(1, "Objective-C"),
                    OptionDto(2, "Swift"),
                    OptionDto(3, "Dart"),
                    OptionDto(4, "Java")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 5,
                title = "Which build system is used in KMM?",
                options = listOf(
                    OptionDto(1, "Maven"),
                    OptionDto(2, "Gradle"),
                    OptionDto(3, "Bazel"),
                    OptionDto(4, "Ant")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            // ---------- JETPACK COMPOSE ----------
            QuestionDto(
                id = 6,
                title = "Jetpack Compose is used for?",
                options = listOf(
                    OptionDto(1, "Backend development"),
                    OptionDto(2, "UI development"),
                    OptionDto(3, "Database management"),
                    OptionDto(4, "Network calls")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 7,
                title = "Jetpack Compose is based on which programming style?",
                options = listOf(
                    OptionDto(1, "Imperative"),
                    OptionDto(2, "Declarative"),
                    OptionDto(3, "Procedural"),
                    OptionDto(4, "Functional only")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 8,
                title = "Which function annotation is required in Compose?",
                options = listOf(
                    OptionDto(1, "@Ui"),
                    OptionDto(2, "@Composable"),
                    OptionDto(3, "@ComposeView"),
                    OptionDto(4, "@Layout")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 9,
                title = "What handles state in Jetpack Compose?",
                options = listOf(
                    OptionDto(1, "LiveData only"),
                    OptionDto(2, "State and MutableState"),
                    OptionDto(3, "XML"),
                    OptionDto(4, "Intent")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 10,
                title = "Which layout replaces LinearLayout in Compose?",
                options = listOf(
                    OptionDto(1, "Row and Column"),
                    OptionDto(2, "ConstraintLayout"),
                    OptionDto(3, "FrameLayout"),
                    OptionDto(4, "RelativeLayout")
                ),
                correctOptionId = 1,
                isSelected = false
            ),

            // ---------- SWIFTUI ----------
            QuestionDto(
                id = 11,
                title = "SwiftUI is used to build UI for which platform?",
                options = listOf(
                    OptionDto(1, "Android"),
                    OptionDto(2, "Web"),
                    OptionDto(3, "Apple platforms"),
                    OptionDto(4, "Windows")
                ),
                correctOptionId = 3,
                isSelected = false
            ),

            QuestionDto(
                id = 12,
                title = "SwiftUI follows which UI approach?",
                options = listOf(
                    OptionDto(1, "Imperative"),
                    OptionDto(2, "Declarative"),
                    OptionDto(3, "Procedural"),
                    OptionDto(4, "XML based")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 13,
                title = "Which keyword is used to create a view in SwiftUI?",
                options = listOf(
                    OptionDto(1, "class"),
                    OptionDto(2, "struct"),
                    OptionDto(3, "interface"),
                    OptionDto(4, "enum")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 14,
                title = "Which property wrapper is used for state in SwiftUI?",
                options = listOf(
                    OptionDto(1, "@State"),
                    OptionDto(2, "@BindingOnly"),
                    OptionDto(3, "@UIState"),
                    OptionDto(4, "@Mutable")
                ),
                correctOptionId = 1,
                isSelected = false
            ),

            QuestionDto(
                id = 15,
                title = "Which view is used for vertical layout in SwiftUI?",
                options = listOf(
                    OptionDto(1, "HStack"),
                    OptionDto(2, "VStack"),
                    OptionDto(3, "ZStack"),
                    OptionDto(4, "List")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            // ---------- MIXED ----------
            QuestionDto(
                id = 16,
                title = "Compose and SwiftUI are both?",
                options = listOf(
                    OptionDto(1, "Imperative UI frameworks"),
                    OptionDto(2, "Declarative UI frameworks"),
                    OptionDto(3, "XML based"),
                    OptionDto(4, "Backend frameworks")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 17,
                title = "Which language is used in Jetpack Compose?",
                options = listOf(
                    OptionDto(1, "Java"),
                    OptionDto(2, "Kotlin"),
                    OptionDto(3, "Swift"),
                    OptionDto(4, "Dart")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 18,
                title = "Which language is used in SwiftUI?",
                options = listOf(
                    OptionDto(1, "Objective-C"),
                    OptionDto(2, "Swift"),
                    OptionDto(3, "Kotlin"),
                    OptionDto(4, "Java")
                ),
                correctOptionId = 2,
                isSelected = false
            ),

            QuestionDto(
                id = 19,
                title = "KMM helps to share code between?",
                options = listOf(
                    OptionDto(1, "Android and Web"),
                    OptionDto(2, "iOS and Web"),
                    OptionDto(3, "Android and iOS"),
                    OptionDto(4, "Web and Desktop")
                ),
                correctOptionId = 3,
                isSelected = false
            ),

            QuestionDto(
                id = 20,
                title = "Which UI framework uses @Composable?",
                options = listOf(
                    OptionDto(1, "SwiftUI"),
                    OptionDto(2, "Jetpack Compose"),
                    OptionDto(3, "Flutter"),
                    OptionDto(4, "React Native")
                ),
                correctOptionId = 2,
                isSelected = false
            )
        )
        return flow {
            delay(4000) // Network simulation
            emit(questions.map { it.toDomain() })
        }
    }
}