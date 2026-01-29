//
//  PlaceholderPhotosListScreen.swift
//  iosApp
//
//  Created by Mohit Varma on 28/01/26.
//

import SwiftUI
import Shared
import Combine

class PlaceholderPhotosViewModelWrapper : ObservableObject {
    @Published var photos : [Photo] = []
    @Published var isLoading : Bool = true
    private let placeHolderViewModel : PlaceholderPhotosViewModel
    private var cancellations = Set<AnyCancellable>()
    
    
    init() {
        self.placeHolderViewModel = PlaceholderPhotosViewModelFactory().create()
        
        createPublisher(
            placeHolderViewModel.photosResult
        )
        .receive(on: DispatchQueue.main)
        .sink { [weak self] state in
            self?.photos = state.photos
            self?.isLoading = state.isLoading
        }
        .store(in: &cancellations)
    }
    
}

struct PlaceholderPhotosListScreen: View {
    
    @StateObject var placeHolderViewModelWrapper = PlaceholderPhotosViewModelWrapper()
    
    var body: some View {
        if (placeHolderViewModelWrapper.isLoading) {
            VStack {
                ProgressView()
                Text("Loading...")
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        }else if (placeHolderViewModelWrapper.photos.isEmpty) {
            Text("Data Not Available")
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        }
        else {
            ScrollView {
                LazyVStack(alignment : .leading) {
                    ForEach(placeHolderViewModelWrapper.photos, id : \.id) { element in
                        HStack {
                            Text(element.id.description)
                                .frame(maxWidth : 48, maxHeight: 48, alignment : .center)
                                .background(Color.blue)
                                .foregroundColor(Color.white)
                                .clipShape(RoundedRectangle(cornerRadius: 10, style: .circular))
                            
                            VStack {
                                Text(element.title)
                                    .fontWeight(.bold)
                                    .padding()
                                    .frame(maxWidth: .infinity, alignment: .leading)
                            }
                            AsyncImage(url: URL(string: "https://picsum.photos/200/300")) { image in
                                image
                                    .frame(maxWidth: 80, maxHeight: 80, alignment: .center)
                                    . clipShape(RoundedRectangle(cornerRadius: 50, style: .circular))
                            } placeholder: {
                                Text("Loading...")
                            }

                        }.padding(.horizontal, 10)
                    }
                }
            }
        }
    }
}

#Preview {
    PlaceholderPhotosListScreen()
}

