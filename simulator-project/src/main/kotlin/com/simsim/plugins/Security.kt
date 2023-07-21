package com.simsim.plugins

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.encodeURLPath
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.host
import io.ktor.server.application.install
import io.ktor.server.application.port
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.oauth
import io.ktor.server.auth.principal
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.netty.EngineMain
import io.ktor.server.request.uri
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun Application.configureSecurity() {
    SecurityProperties.init(environment.config)
    configureOAuth()
}

val applicationHttpClient = HttpClient(CIO) {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
    install(ContentNegotiation) {
        json()
    }
}

private fun Application.configureOAuth() {
    install(Sessions) {
        cookie<UserSession>("user-session")
    }

    val redirects = mutableMapOf<String, String>()

    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = SecurityProperties.OAuth.Google.GOOGLE,
                    authorizeUrl = SecurityProperties.OAuth.Google.AUTHORIZE_URL,
                    accessTokenUrl = SecurityProperties.OAuth.Google.ACCESS_TOKEN_URL,
                    requestMethod = HttpMethod.Post,
                    clientId = SecurityProperties.OAuth.Google.clientId,
                    clientSecret = SecurityProperties.OAuth.Google.secret,
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                    onStateCreated = { call, state ->
                        redirects[state] =
                            call.request.queryParameters[SecurityProperties.OAuth.REDIRECT_URL_PARAMETER_NAME]
                                ?: throw IllegalStateException("No redirect URL")
                    },
                )
            }
            client = applicationHttpClient
        }
    }
    routing {
        authenticate("auth-oauth-google") {
            get("login") {
                // Automatic redirect to OAuth provider
            }

            get("/callback") {
                call.principal<OAuthAccessTokenResponse.OAuth2>()?.let {
                    call.sessions.set(UserSession(it.accessToken))
                    call.respondRedirect(redirects[it.state]!!)
                }
            }
        }
    }
}

suspend fun PipelineContext<*, ApplicationCall>.needAuth(): UserInfo {
    val session: UserSession? = call.sessions.get()

    if (session == null) {
        call.respondRedirect("/login?${SecurityProperties.OAuth.REDIRECT_URL_PARAMETER_NAME}=${call.request.uri}")
    }

    return applicationHttpClient.get(SecurityProperties.OAuth.Google.USER_INFO_URL) {
        header(HttpHeaders.Authorization, "Bearer ${session?.accessToken}")
    }.body()
}

class UserSession(val accessToken: String)

@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String,
)

object SecurityProperties {

    fun init(config: ApplicationConfig) {
        OAuth.init(config)
    }

    object OAuth {

        private const val OAUTH = "oauth"

        const val REDIRECT_URL_PARAMETER_NAME = "redirectUrl"

        fun init(config: ApplicationConfig) = config.config(OAUTH).run {
            Google.init(this)
        }

        object Google {

            const val GOOGLE: String = "google"

            const val USER_INFO_URL: String = "https://www.googleapis.com/oauth2/v2/userinfo"

            const val ACCESS_TOKEN_URL: String = "https://accounts.google.com/o/oauth2/token"
            const val AUTHORIZE_URL: String = "https://accounts.google.com/o/oauth2/auth"

            lateinit var clientId: String
            lateinit var secret: String

            fun init(config: ApplicationConfig) = config.config(GOOGLE).run {
                clientId = property(Prefix.CLIENT_ID).getString()
                secret = property(Prefix.SECRET).getString()
            }
        }

        private object Prefix {
            const val CLIENT_ID = "client-id"
            const val SECRET = "client-secret"
        }
    }

}