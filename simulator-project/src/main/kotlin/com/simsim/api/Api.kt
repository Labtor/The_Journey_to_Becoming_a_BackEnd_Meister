package com.simsim.api

import io.ktor.server.application.Application
import io.ktor.server.routing.Routing
import io.ktor.server.routing.routing

abstract class Api(private val route: Routing.() -> Unit) {
    operator fun invoke(app: Application) : Routing = app.routing(route)
}