package com.example.webservice.domains.address.services.beans

import com.example.webservice.commons.PageAttr
import com.example.webservice.domains.address.models.entities.Village
import com.example.webservice.domains.address.repositories.VillageRepo
import com.example.webservice.domains.address.services.VillageService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class VillageServiceImpl @Autowired constructor(
        private val villageRepo: VillageRepo
) : VillageService {

    override fun search(query: String, page: Int): Page<Village> {
        return this.villageRepo.search(query, PageAttr.getPageRequest(page))
    }

    override fun save(entity: Village): Village {
        return this.villageRepo.save(entity)
    }

    override fun find(id: Long): Optional<Village> {
        return this.villageRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.villageRepo.deleteById(id)
            return
        }
        val village: Village = this.find(id).orElseThrow { NotFoundException("Could not find village with id $id") }
        village.isDeleted = true
        this.save(village)
    }

}
