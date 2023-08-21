package com.simsim.domain.api.service

import com.simsim.persistence.api.repo.ApiRepository
import com.simsim.plugins.database.dbQuery
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.util.toMap
import kotlinx.serialization.Serializable

class RunApi(
    private val apiRepository: ApiRepository,
    private val httpClient: HttpClient
) {

    suspend operator fun invoke(id: Int) = dbQuery {
        val api = apiRepository.findById(id)
            ?: throw IllegalArgumentException("Api with id $id does not exist")

        val response = httpClient.request(urlString = api.url) {
            setBody(api.body)
            api.headers.forEach {
                headers.append(it.key, it.value)
            }
            method = HttpMethod(api.method.name)
        }

        return@dbQuery Response(
            millisecondTime = response.responseTime.timestamp - response.requestTime.timestamp,
            statusCode = response.status.value,
            body = response.body(),
            headers = response.headers.toMap()
        )
    }

    @Serializable
    data class Response(
        val millisecondTime: Long,
        val statusCode: Int,
        val body: String,
        val headers: Map<String, List<String>>
    )
}