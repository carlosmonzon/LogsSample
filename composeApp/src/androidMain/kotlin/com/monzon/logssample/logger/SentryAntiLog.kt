package com.monzon.logssample.logger

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.sentry.*
import io.sentry.protocol.Message

class SentryAntiLog(
    private val hub: IHub,
    private val minEventLevel: SentryLevel,
    private val minBreadcrumbLevel: SentryLevel
) : Antilog() {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        if (message.isNullOrEmpty() && throwable == null) {
            return
        }

        val level = getSentryLevel(priority)
        val sentryMessage = Message().apply {
            this.message = message
        }
        captureEvent(
            logLevel = level,
            tag = tag,
            msg = sentryMessage,
            throwable = throwable
        )

        addBreadcrumb(
            logLevel = level,
            msg = sentryMessage,
            throwable = throwable
        )
    }


    private fun captureEvent(
        logLevel: SentryLevel,
        tag: String?,
        msg: Message,
        throwable: Throwable?
    ) {
        if (isLoggable(logLevel, minEventLevel)) {
            val sentryEvent = SentryEvent().apply {
                level = logLevel
                throwable?.let { setThrowable(it) }
                tag?.let {
                    setTag("NapierTag", it)
                }
                message = msg
                logger = "Napier"
            }
            hub.captureEvent(sentryEvent)
        }
    }

    private fun addBreadcrumb(
        logLevel: SentryLevel,
        msg: Message,
        throwable: Throwable?
    ) {
        // checks the breadcrumb level
        if (isLoggable(logLevel, minBreadcrumbLevel)) {
            val throwableMsg = throwable?.message
            val breadCrumb = when {
                msg.message != null -> Breadcrumb().apply {
                    level = logLevel
                    category = "Napier"
                    message = msg.formatted ?: msg.message
                }

                throwableMsg != null -> Breadcrumb.error(throwableMsg).apply {
                    category = "exception"
                }

                else -> null
            }
            breadCrumb?.let { hub.addBreadcrumb(it) }
        }
    }

    private fun isLoggable(
        level: SentryLevel,
        minLevel: SentryLevel
    ): Boolean = level.ordinal >= minLevel.ordinal

    private fun getSentryLevel(logLevel: LogLevel): SentryLevel {
        return when (logLevel) {
            LogLevel.ASSERT -> SentryLevel.FATAL
            LogLevel.ERROR -> SentryLevel.ERROR
            LogLevel.WARNING -> SentryLevel.WARNING
            LogLevel.INFO -> SentryLevel.INFO
            LogLevel.DEBUG -> SentryLevel.DEBUG
            LogLevel.VERBOSE -> SentryLevel.DEBUG
            else -> SentryLevel.DEBUG
        }
    }

}