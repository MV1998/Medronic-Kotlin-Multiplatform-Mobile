import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            PlaceholderPhotosListScreen()
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
                .background(Color.white)
        }
    }
}
