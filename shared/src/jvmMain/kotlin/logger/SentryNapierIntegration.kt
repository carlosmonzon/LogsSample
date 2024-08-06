import io.github.aakira.napier.Napier
import io.sentry.*

class SentryNapierIntegration(
    val minEventLevel: SentryLevel = SentryLevel.ERROR,
    val minBreadcrumbLevel: SentryLevel = SentryLevel.INFO
) : Integration {
    override fun register(hub: IHub, options: SentryOptions) {
        val antiLog = SentryAntiLog(
            hub = hub,
            minEventLevel = minEventLevel,
            minBreadcrumbLevel = minBreadcrumbLevel
        )
        Napier.base(antiLog)
    }

}