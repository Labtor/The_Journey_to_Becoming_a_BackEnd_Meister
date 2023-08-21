package com.simsim.persistence.api

import com.simsim.domain.api.model.Api
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object ApiTable : IntIdTable() {
    val name = varchar("name", Api.NAME_MAX_LENGTH)
    val description = varchar("description", Api.DESCRIPTION_MAX_LENGTH)
    val url = text("url")
    val method = enumeration("method", Method::class)
    val body = text("body")
    val headers = text("headers")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

enum class Method {
    GET, POST, PUT, DELETE
}