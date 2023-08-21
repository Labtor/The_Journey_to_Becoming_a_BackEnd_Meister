package com.simsim.presentation

import io.ktor.server.application.Application
import io.ktor.server.routing.Routing
import io.ktor.server.routing.routing

abstract class Controller(private val route: Routing.() -> Unit) {
    operator fun invoke(app: Application) : Routing = app.routing(route)
}