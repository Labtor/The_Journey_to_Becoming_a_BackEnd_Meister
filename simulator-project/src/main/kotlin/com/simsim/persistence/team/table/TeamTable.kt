package com.simsim.persistence.team.table

import org.jetbrains.exposed.dao.id.IntIdTable

object TeamTable : IntIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 255).nullable()
}