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
        }
        .store(in: &cancellations)
    }
    
    func onNext() {
        quizViewModel.onNext()
    }
    
    func onOptionSelect(id : Int) {
        quizViewModel.onQuestionSelect(selectedOptionId: Int32(id))
    }

}

struct QuizAppScreen: View {
    
    @StateObject private var quizAppViewModelWrapper = QuizAppViewModelWrapper()
    @Environment(\.colorScheme) var colorScheme
    
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
                    Text("Question 06/20")
                        .foregroundColor(.questionTxt)
                        .fontWeight(.bold)
                        .font(.system(size: 28))
                }
                
                LazyHStack(alignment : .top) {
                    ForEach(0..<20) { index in
                        Divider()
                            .frame(width: 10, height: 2, alignment: .leading)
                            .background(index % 2 == 0 ? .green : .red)
                    }
                }
                .frame(maxWidth: .infinity,maxHeight: 2, alignment: .center)
                
                Spacer()
                    .frame(height: 100)
                
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
                                        quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId == -1 ? .gray :
                                            quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId ==
                                        option.id ? .green : .red
                                    )
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                Image(systemName:
                                        quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId == -1 ? "circle" :
                                            quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId ==
                                      option.id ? "checkmark.circle.fill" : "xmark.circle.fill") //xmark, checkmark
                                    .foregroundColor(
                                        quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId == -1 ? .gray :
                                            quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId ==
                                        option.id ? .green : .red
                                    )
                            }
                            .padding()
                            .background(
                                RoundedRectangle(cornerRadius: 12)
                                    .strokeBorder(
                                        quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId == -1 ? .gray :
                                            quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId ==
                                        option.id ? .green : .red
                                            , lineWidth: 2)
                            )
                            .frame(maxWidth: .infinity, alignment: .top)
                        }
                        .buttonStyle(.plain)
                        .disabled(quizAppViewModelWrapper.currentQuestion!.userSelectedOptionId != -1)
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
                
                if (!quizAppViewModelWrapper.isLastQuestion) {
                    Button {
                        quizAppViewModelWrapper.onNext()
                    } label: {
                        Text("Next")
                    }
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                    )
                    .frame(maxWidth: .infinity, alignment: .trailing)
                }
            }
            .padding()
            .frame(maxWidth : .infinity, maxHeight: .infinity, alignment: .top)
            .background(.quizBackground)
        }
    }
}

#Preview {
    QuizAppScreen()
}
