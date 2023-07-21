package com.simsim.api

import com.simsim.plugins.needAuth
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route

class UserApi : Api({
    route("/user") {
        get("/login") {
            val userInfo = needAuth()
            call.respond(userInfo)
        }
    }
})