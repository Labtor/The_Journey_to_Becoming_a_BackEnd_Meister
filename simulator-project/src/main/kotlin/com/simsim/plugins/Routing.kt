package com.simsim.plugins

import com.simsim.presentation.BaseController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.getKoin

fun Application.configureRouting() {
    getKoin().getAll<BaseController>().forEach {
        it(this)
    }

    routing {
        get {
            call.respond("Hello World!")
        }
    }

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
