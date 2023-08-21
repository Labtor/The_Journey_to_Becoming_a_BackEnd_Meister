package com.simsim.plugins

import com.simsim.plugins.database.connectExposed
import io.ktor.server.application.Application

fun Application.configureEvents() {
    environment.monitor.run {
        connectExposed()
    }
}