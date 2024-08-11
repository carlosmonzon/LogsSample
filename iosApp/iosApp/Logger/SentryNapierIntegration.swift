//
//  SentryNapierIntegration.swift
//  iosApp
//
//  Created by Carlos Monzon on 10/8/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Sentry
import ComposeApp

class SentryNapierIntegration : NSObject, SentryIntegrationProtocol {
    
    let minEventLevel: SentryLevel = SentryLevel.error
    let minBreadcrumbLevel: SentryLevel = SentryLevel.info
    
    func install(with options: Options) -> Bool {
        let antiLog = SentryAntiLog(minEventLevel: minEventLevel, minBreadcumbLevel: minBreadcrumbLevel)
        LogKt.releaseBuild(antilog: antiLog)
        return true
    }
    
    func uninstall() {}
}
