package com.simsim.persistence.api.repo

import com.simsim.domain.api.model.Api

interface ApiRepository {

    fun save(api: Api): Int

    fun findById(id: Int): Api?

}