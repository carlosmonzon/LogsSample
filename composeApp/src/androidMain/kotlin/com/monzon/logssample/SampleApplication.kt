package com.monzon.logssample

import android.app.Application
import com.monzon.logssample.logger.SentryNapierIntegration
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SentryAndroid.init(this) { options ->
            options.dsn = "YOUR_DSN_KEY"
            options.addIntegration(
                SentryNapierIntegration(
                    minEventLevel = SentryLevel.ERROR,
                    minBreadcrumbLevel = SentryLevel.INFO
                )
            )
        }
    }
} 