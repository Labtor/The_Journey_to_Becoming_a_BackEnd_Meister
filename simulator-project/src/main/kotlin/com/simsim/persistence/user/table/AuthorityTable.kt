package com.simsim.persistence.user.table

import com.simsim.persistence.team.table.TeamTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object AuthorityTable : Table() {
    val userId: Column<Int> = integer("userid").references(UserTable.id)
    val teamId: Column<Int> = integer("teamId").references(TeamTable.id)
    val permission: Column<Permission> = enumerationByName("permission", Permission.MAX_LENGTH)
}

enum class Permission {
    VIEW,
    EDIT,
    ADMIN,

    ;

    companion object {
        const val MAX_LENGTH = 5
    }
}