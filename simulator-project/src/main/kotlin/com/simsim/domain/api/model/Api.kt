package com.simsim.domain.api.model

import com.simsim.persistence.api.Method
import java.time.LocalDateTime

data class Api(
    val id: Int,
    val name: String,
    val description: String,
    val url: String,
    val method: Method,
    val body: String,
    val headers: Map<String, String>,
    val created: LocalDateTime,
    val updated: LocalDateTime
) {

    init {
        if(name.length > NAME_MAX_LENGTH)
            throw IllegalArgumentException("Name cannot be longer than $NAME_MAX_LENGTH characters")

        if(description.length > DESCRIPTION_MAX_LENGTH)
            throw IllegalArgumentException("Description cannot be longer than $DESCRIPTION_MAX_LENGTH characters")
    }

    companion object {
        fun new(
            name: String,
            description: String,
            url: String,
            method: Method,
            body: String,
            headers: Map<String, String>
        ) = Api(
            id = 0,
            name = name,
            description = description,
            url = url,
            method = method,
            body = body,
            headers = headers,
            created = LocalDateTime.now(),
            updated = LocalDateTime.now()
        )

        const val NAME_MAX_LENGTH = 50
        const val DESCRIPTION_MAX_LENGTH = 255
    }
}