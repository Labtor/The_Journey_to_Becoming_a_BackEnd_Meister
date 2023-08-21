package com.simsim.persistence.api.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

interface BaseMapper<T : Table, M> {

    fun toModel(row: ResultRow): M

}