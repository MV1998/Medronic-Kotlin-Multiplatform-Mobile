import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationStack() {
                List {
                    NavigationLink("Fruit List") {
                        FruitListAppWithSearch()
                    }
                    NavigationLink("Place Holder Photos List") {
                        PlaceholderPhotosListScreen()
                    }
                    NavigationLink("Quiz App") {
                        QuizAppScreen()
                    }
                }
            }
            .navigationTitle("Swift UI")
        }
    }
}
