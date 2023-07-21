package com.simsim.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is IllegalArgumentException -> call.respond(HttpStatusCode.BadRequest)
                is IllegalStateException -> call.respond(HttpStatusCode.BadRequest)

                is NotImplementedError -> call.respond(HttpStatusCode.NotImplemented)

                else -> call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}
