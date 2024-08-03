import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        LogKt.debugBuild()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
