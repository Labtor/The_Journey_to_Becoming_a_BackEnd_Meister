package com.simsim.persistence.user.table

import com.simsim.domain.user.model.User
import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable() {
    val name = varchar("name", User.NAME_MAX_LENGTH)
    val email = varchar("email", User.EMAIL_MAX_LENGTH).uniqueIndex("email_index")
}