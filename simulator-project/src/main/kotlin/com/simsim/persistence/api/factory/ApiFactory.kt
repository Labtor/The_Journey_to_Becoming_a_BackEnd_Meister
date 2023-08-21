package com.simsim.persistence.api.factory

import com.simsim.domain.api.model.Api
import com.simsim.persistence.api.ApiTable
import com.simsim.persistence.api.mapper.ApiMapper
import com.simsim.persistence.api.repo.ApiRepository
import com.simsim.plugins.database.insertOrUpdate
import org.jetbrains.exposed.sql.select

class ApiFactory : ApiRepository {

    override fun save(api: Api): Int = ApiTable.insertOrUpdate {
        it[id] = api.id
        it[name] = api.name
        it[description] = api.description
        it[url] = api.url
        it[method] = api.method
        it[body] = api.body
        it[headers] = api.headers.toString()
        it[createdAt] = api.created
        it[updatedAt] = api.updated
    }.value

    override fun findById(id: Int): Api? = ApiTable
        .select { ApiTable.id eq id }
        .firstNotNullOfOrNull(ApiMapper::toModel)




}