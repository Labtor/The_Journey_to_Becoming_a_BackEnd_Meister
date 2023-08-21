package com.simsim

import io.ktor.server.application.*
import io.ktor.server.netty.*
import com.simsim.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureEvents()
    configureInject()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureRouting()
}
