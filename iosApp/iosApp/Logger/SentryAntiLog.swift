//
//  SentryAntiLog.swift
//  iosApp
//
//  Created by Carlos Monzon on 10/8/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import Sentry

class SentryAntiLog: NapierAntilog {
    
    let minEventLevel: SentryLevel
    let minBreadcumbLevel: SentryLevel
    
    init(minEventLevel: SentryLevel, minBreadcumbLevel: SentryLevel) {
        self.minEventLevel = minEventLevel
        self.minBreadcumbLevel = minBreadcumbLevel
    }
    
    override func performLog(priority: NapierLogLevel, tag: String?, throwable: KotlinThrowable?, message: String?) {
        guard message != nil || throwable != nil else { return }
        let level = getSentryLevel(level: priority)
        captureEvent(logLevel: level, tag: tag, msg: message ?? "", error: throwable?.asError())
        addBreadcrumb(logLevel: level, tag: tag, message: message, error: throwable?.asError())
    }
    
    private func addBreadcrumb(logLevel: SentryLevel, tag: String?, message: String?, error: Error?) {
        if (isLoggable(level: logLevel, minLevel: minBreadcumbLevel)) {
            let breadcrumb = Breadcrumb(level: logLevel, category: "Napier")
            breadcrumb.message = message
            if let error {
                breadcrumb.message = error.localizedDescription
            } else {
                breadcrumb.message = message
            }
            SentrySDK.addBreadcrumb(breadcrumb)
        }
    }
    
    private func captureEvent(
            logLevel: SentryLevel,
            tag: String?,
            msg: String,
            error: Error?
    ) {
        if (isLoggable(level: logLevel, minLevel: minEventLevel)) {
            let event = Event()
            event.level = logLevel
            event.error = error
            if let tag {
                event.tags = ["NapierTag": tag]
            }
            event.message = SentryMessage(formatted: msg)
            SentrySDK.capture(event: event)
        }
    }
    
    private func isLoggable(level: SentryLevel, minLevel: SentryLevel) -> Bool {
        level.rawValue >= minLevel.rawValue
    }
    
    private func getSentryLevel(level: NapierLogLevel) -> SentryLevel {
        switch level {
        case .debug:
            return SentryLevel.debug
        case .info:
            return SentryLevel.info
        case .warning:
            return SentryLevel.warning
        case .error:
            return SentryLevel.error
        case .assert:
            return SentryLevel.fatal
        default:
            return SentryLevel.debug
        
        }
    }
}
