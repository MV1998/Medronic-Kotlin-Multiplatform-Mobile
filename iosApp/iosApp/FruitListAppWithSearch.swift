//
//  FruitListAppWithSearch.swift
//  iosApp
//
//  Created by Mohit Varma on 28/01/26.
//

import SwiftUI
import Shared
import Combine


class FruitViewModelWrapper : ObservableObject {
    
    @Published var filterFruits : [String] = []
    @Published var isLoading : Bool = true
    
    private let fruitViewModel : FruitViewModel
    private var cancellation = Set<AnyCancellable>()

    init() {
        self.fruitViewModel = FruitViewModelFactory().create()
        
        createPublisher(fruitViewModel.fruitState)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] uiState in
                self?.isLoading = uiState.isLoading
                self?.filterFruits = uiState.list
            }
            .store(in: &cancellation)
    }
 
    func search(searchQuery query: String) {
        fruitViewModel.onAction(uiAction: UiAction.OnQuery(query: query))
    }
    
}


struct FruitListAppWithSearch: View {
    
    @StateObject private var fruitViewModel = FruitViewModelWrapper()
    @State private var fruitName = ""
    @FocusState private var focused : Bool
    @FocusState private var searchFocused : Bool
    @State private var uiState : UIState?
    @State private var searchedText = ""
    
    var body: some View {
        VStack(alignment : .leading) {
            Text("Search Fruit").font(.system(size: 12)).foregroundColor(Color.black)
            TextField("", text: $searchedText)
            .focused($searchFocused)
            .padding()
            .background(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(searchFocused ? .blue : .gray)
            )
            .onChange(of: searchedText) { oldValue, newValue in
                fruitViewModel.search(searchQuery: newValue)
            }
            if (fruitViewModel.isLoading) {
                VStack {
                    ProgressView()
                    Text("Loading...")
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            }else if (fruitViewModel.filterFruits.isEmpty) {
                Text("No Fruit Found with this search")
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            }
            else {
                ScrollView {
                    LazyVStack(alignment : .leading) {
                        ForEach(fruitViewModel.filterFruits, id : \.self) { element in
                            Text(element)
                                .fontWeight(.bold)
                                .padding()
                                .frame(maxWidth: .infinity, alignment: .leading)
                            Divider()
                        }
                    }
                }
                .onAppear {
                    print("\(fruitViewModel.filterFruits)")
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}

#Preview {
    FruitListAppWithSearch()
}
