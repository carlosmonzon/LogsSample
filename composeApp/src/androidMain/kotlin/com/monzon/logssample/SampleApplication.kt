package com.monzon.logssample

import SentryNapierIntegration
import android.app.Application
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SentryAndroid.init(this) { options ->
            options.dsn =
                "YOUR_PROJECT_SENTRY_DSN"
            options.addIntegration(
                SentryNapierIntegration(
                    minEventLevel = SentryLevel.ERROR,
                    minBreadcrumbLevel = SentryLevel.INFO
                )
            )
        }
    }
} 