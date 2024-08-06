import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.sentry.Sentry
import io.sentry.SentryLevel

fun main() = application {
    setupSentry()
    Window(
        onCloseRequest = ::exitApplication,
        title = "LogsSample",
    ) {
        App()
    }
}

fun setupSentry() {
    Sentry.init { options ->
        options.dsn =
            "YOUR_PROJECT_SENTRY_DSN"
        options.addIntegration(
            SentryNapierIntegration(
                minEventLevel = SentryLevel.ERROR,
                minBreadcrumbLevel = SentryLevel.INFO
            )
        )
        options.isDebug = true
    }
}