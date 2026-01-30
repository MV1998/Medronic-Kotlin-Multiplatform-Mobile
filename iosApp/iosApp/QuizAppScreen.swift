//
//  QuizAppScreen.swift
//  iosApp
//
//  Created by Mohit Varma on 29/01/26.
//

import SwiftUI
import Combine
import Shared

class QuizAppViewModelWrapper : ObservableObject {
    
    private let quizViewModel : QuizViewModel
    
    @Published var currentQuestionIndex = 0
    @Published var isLoading : Bool = true
    @Published var isLastQuestion : Bool = false
    @Published var currentQuestion : Question? = nil
    @Published var totalAnswered : Int = 0
    @Published var totalQuestion : Int = 0
    @Published var questionList : [Question] = []
    
    private var cancellations = Set<AnyCancellable>()
    
    init() {
        self.quizViewModel = QuizViewModelFactory().create()
        
        createPublisher(
            quizViewModel.quizState
        )
        .receive(on: DispatchQueue.main)
        .sink { [weak self] state in
            self?.currentQuestionIndex  = Int(state.currentQuestionIndex)
            self?.isLoading = state.isLoading
            self?.currentQuestion = state.currentQuestion
            self?.isLastQuestion = state.isLastQuestion
            self?.totalAnswered = Int(state.totalWrongCount + state.totalCorrectCount)
            self?.totalQuestion = Int(state.quizList.count)
            self?.questionList = state.quizList
        }
        .store(in: &cancellations)
    }
    
    func onNext() {
        quizViewModel.onNext()
    }
    
    func onPrevious() {
        quizViewModel.onPrevious()
    }
    
    func onOptionSelect(id : Int) {
        quizViewModel.onQuestionSelect(selectedOptionId: Int32(id))
    }

}

struct QuizAppScreen: View {
    
    @StateObject private var quizAppViewModelWrapper = QuizAppViewModelWrapper()
    @Environment(\.colorScheme) var colorScheme
    @Environment(\.dismiss) private var dismiss
    @State private var showAlert = false
    
    var body: some View {
        if (quizAppViewModelWrapper.isLoading) {
            VStack {
                ProgressView()
                    .foregroundColor(.questionTxt)
                Text("Loading...")
                    .foregroundColor(.questionTxt)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            .background(.quizBackground)
        }else if (quizAppViewModelWrapper.currentQuestion == nil) {
            Text("Data Not Available")
                .foregroundColor(.questionTxt)
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        }
        else {
            VStack(alignment : .leading) {
                Text("KMM Quiz")
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .foregroundColor(.questionTxt.opacity(0.5))
                Spacer()
                HStack {
                    Text("Question \(quizAppViewModelWrapper.totalAnswered)/\(quizAppViewModelWrapper.totalQuestion)")
                        .foregroundColor(.questionTxt)
                        .fontWeight(.bold)
                        .font(.system(size: 28))
                }
                
                LazyHStack(alignment : .top) {
                    
                    ForEach(quizAppViewModelWrapper.questionList.indices, id: \.self) { index in
                            Divider()
                                    .frame(width: 10, height: 2, alignment: .leading)
                                    .background(divideColor(index: index))
                    }
                }
                .frame(maxWidth: .infinity,maxHeight: 2, alignment: .center)
                
                VStack{
                    if let currentQuestion = quizAppViewModelWrapper.currentQuestion, currentQuestion.isAnswered {
                        Image(systemName:
                                currentQuestion.isCorrect ?
                              "checkmark.circle.fill" : "xmark.circle.fill")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 48, height: 48, alignment: .center)
                        .foregroundColor(currentQuestion.isCorrect ? .green : .red)
                        Text(currentQuestion.isCorrect ? "Correct" : "Wrong")
                            .foregroundColor(currentQuestion.isCorrect ? .green : .red)
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: 100, alignment: .center)
                
                Text(quizAppViewModelWrapper.currentQuestion!.title)
                    .foregroundColor(.questionTxt)
                    .fontWeight(.bold)
                    .font(.system(size: 18))
                
                Spacer()
                    .frame(height: 50)
                
                LazyVStack {
                    ForEach(quizAppViewModelWrapper.currentQuestion!.options, id: \.id) { option in
                        Button {
                            quizAppViewModelWrapper.onOptionSelect(id: Int(option.id))
                        } label: {
                            HStack {
                                Text(option.title)
                                    .foregroundColor(
                                        !quizAppViewModelWrapper.currentQuestion!.isAnswered ? .gray :
                                            quizAppViewModelWrapper.currentQuestion!.correctOptionId ==
                                        option.id ? .green : .red
                                    )
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                Image(systemName:
                                        !quizAppViewModelWrapper.currentQuestion!.isAnswered ? "circle" :
                                            quizAppViewModelWrapper.currentQuestion!.correctOptionId ==
                                      option.id ? "checkmark.circle.fill" : "xmark.circle.fill") //xmark, checkmark
                                    .foregroundColor(
                                        !quizAppViewModelWrapper.currentQuestion!.isAnswered ? .gray :
                                            quizAppViewModelWrapper.currentQuestion!.correctOptionId ==
                                        option.id ? .green : .red
                                    )
                            }
                            .padding()
                            .background(
                                RoundedRectangle(cornerRadius: 12)
                                    .strokeBorder(
                                        !quizAppViewModelWrapper.currentQuestion!.isAnswered ? .gray :
                                            quizAppViewModelWrapper.currentQuestion!.correctOptionId ==
                                        option.id ? .green : .red
                                            , lineWidth: 2)
                            )
                            .frame(maxWidth: .infinity, alignment: .top)
                        }
                        .buttonStyle(.plain)
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .bottom)
                
                
                Spacer()
                    .frame(height: 60)
                
                VStack {
                    HStack {
                        if (quizAppViewModelWrapper.currentQuestionIndex > 0) {
                            Button {
                                quizAppViewModelWrapper.onPrevious()
                            } label: {
                                Text("Previous")
                                    .foregroundColor(.questionTxt)
                            }
                            .padding()
                            .background(
                                RoundedRectangle(cornerRadius: 10)
                                    .fill(.blue)
                            )
                            .frame(maxWidth: .infinity, alignment: .leading)
                        }
                        
                        if (!quizAppViewModelWrapper.isLastQuestion) {
                            
                            Button {
                                quizAppViewModelWrapper.onNext()
                            } label: {
                                Text("Next")
                                    .foregroundColor(.questionTxt)
                            }
                            .padding()
                            .background(
                                RoundedRectangle(cornerRadius: 10)
                                    .fill(.blue)
                            )
                            .frame(maxWidth: .infinity, alignment: .trailing)
                        }
                        
                    }
                    .frame(maxWidth: .infinity)
                    
                    Button {
                        showAlert = true
                    } label: {
                        Text("Quit Quiz")
                            .foregroundColor(.questionTxt)
                    }
                    .buttonStyle(.plain)
                }
                
            }
            .padding()
            .frame(maxWidth : .infinity, maxHeight: .infinity, alignment: .top)
            .background(.quizBackground)
            .alert("Do you want to quit?", isPresented: $showAlert) {
                VStack {
                    Button("No", role: .cancel) {
                        showAlert = false
                    }
                    Button("Yes", role: .destructive) {
                        showAlert = false
                        dismiss()
                    }
                }
            } message : {
                Text("You will lost your score.")
            }
            .navigationBarBackButtonHidden(true)
        }
    }
    
    func divideColor(index : Int) -> Color {
        return quizAppViewModelWrapper.questionList[index].isAnswered
        ? quizAppViewModelWrapper.questionList[index].isCorrect ? .green : .red
        : quizAppViewModelWrapper.currentQuestionIndex == index ? .questionTxt : .gray
    }
}

#Preview {
    QuizAppScreen()
}
