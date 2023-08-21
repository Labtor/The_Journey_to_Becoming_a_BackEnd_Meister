package com.simsim.plugins

import com.simsim.domain.api.service.CreateApi
import com.simsim.domain.api.service.RunApi
import com.simsim.persistence.api.factory.ApiFactory
import com.simsim.persistence.api.repo.ApiRepository
import com.simsim.presentation.ApiController
import com.simsim.presentation.BaseController
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.*
import io.ktor.server.application.Application
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun Application.configureInject() {
    startKoin {
        modules(
            includeApi(),
            includeHttpClient(),
            includeFactory()
        )
    }
}

private fun KoinApplication.includeApi() = module {
    singleOf(::CreateApi)
    singleOf(::RunApi)

    singleOf(::ApiController) bind BaseController::class
}

private fun KoinApplication.includeFactory() = module {
    singleOf(::ApiFactory) bind ApiRepository::class
}

private fun KoinApplication.includeHttpClient() = module {
    single {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }

            expectSuccess = false
        }
    }
}