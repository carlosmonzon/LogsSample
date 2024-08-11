import SwiftUI
import Sentry

@main
struct iOSApp: App {
    init() {
        startSentry()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}


extension App {
    func startSentry() {
        SentrySDK.start { options in
            options.dsn = "YOUR_DSN_KEY"
            options.debug = true // Enabled debug when first installing is always helpful
            SentryNapierIntegration().install(with: options)
        }
    }
}
