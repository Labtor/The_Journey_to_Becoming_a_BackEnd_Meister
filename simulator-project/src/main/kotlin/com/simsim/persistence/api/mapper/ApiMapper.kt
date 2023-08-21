package com.simsim.persistence.api.mapper

import com.simsim.domain.api.model.Api
import com.simsim.persistence.api.ApiTable
import org.jetbrains.exposed.sql.ResultRow

object ApiMapper : BaseMapper<ApiTable, Api> {

    override fun toModel(row: ResultRow): Api {
        return Api(
            id = row[ApiTable.id].value,
            name = row[ApiTable.name],
            description = row[ApiTable.description],
            url = row[ApiTable.url],
            method = row[ApiTable.method],
            body = row[ApiTable.body],
            headers = decodeHeaders(row[ApiTable.headers]),
            created = row[ApiTable.createdAt],
            updated = row[ApiTable.updatedAt]
        )
    }

    private fun decodeHeaders(headers: String): Map<String, String> {
        return headers.replace("""^\{|\}$""".toRegex(), "").ifBlank { return emptyMap() }
            .split(", ")
            .associate {
                val (key, value) = it.split("=")
                key to value
            }
    }
}