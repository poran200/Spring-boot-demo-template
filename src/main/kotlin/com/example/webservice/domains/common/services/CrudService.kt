package com.example.webservice.domains.common.services

import com.example.webservice.domains.common.models.entities.base.BaseEntity
import org.springframework.data.domain.Page
import java.util.*

interface CrudService<T : BaseEntity> {
    fun search(query: String, page: Int): Page<T>
    fun save(entity: T): T
    fun find(id: Long): Optional<T>
    fun delete(id: Long, softDelete: Boolean)
}