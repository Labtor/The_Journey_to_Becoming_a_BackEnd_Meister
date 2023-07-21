package com.simsim.domain.user.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
) {
    companion object {
        const val NAME_MAX_LENGTH = 50
        const val EMAIL_MAX_LENGTH = 255
    }
}