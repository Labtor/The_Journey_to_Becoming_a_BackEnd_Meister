package com.simsim

import com.simsim.api.UserApi
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.simsim.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureEvents()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureRouting()
    UserApi().invoke(this)
}
