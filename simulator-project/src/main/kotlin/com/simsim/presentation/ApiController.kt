package com.simsim.presentation

import com.simsim.domain.api.service.CreateApi
import com.simsim.domain.api.service.RunApi
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

class ApiController(
    val createApi: CreateApi,
    val runApi: RunApi
) : BaseController({
    route("/api") {
        post {
            val request = call.receive<CreateApi.Request>()
            val response = createApi(request)
            call.respond(response)
        }
        post("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid id")

            val response = runApi(id)
            call.respond(response)
        }
    }
})