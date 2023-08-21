package com.simsim.domain.api.service

import com.simsim.domain.api.model.Api
import com.simsim.persistence.api.Method
import com.simsim.persistence.api.repo.ApiRepository
import com.simsim.plugins.database.dbQuery
import kotlinx.serialization.Serializable

class CreateApi(
    private val apiRepository: ApiRepository
) {

    suspend operator fun invoke(request: Request) = dbQuery {
        val api = request.run {
            Api.new(
                name = name,
                description = description,
                url = url,
                method = method,
                body = body,
                headers = headers
            )
        }

        val id = apiRepository.save(api)

        return@dbQuery Response(id)
    }

    data class Request(
        val name: String,
        val url: String,
        val method: Method,
        val headers: Map<String, String>,
        val body: String,
        val description: String
    )

    @Serializable
    data class Response(
        val id: Int
    )
}