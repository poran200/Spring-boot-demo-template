package com.example.webservice.domains.address.services.beans

import com.example.webservice.commons.PageAttr
import com.example.webservice.domains.address.models.entities.District
import com.example.webservice.domains.address.repositories.DistrictRepo
import com.example.webservice.domains.address.services.DistrictService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class DistrictServiceImpl @Autowired constructor(
        private val districtRepo: DistrictRepo
) : DistrictService {

    override fun save(entity: District): District {
        return this.districtRepo.save(entity)
    }

    override fun search(query: String, page: Int): Page<District> {
        return this.districtRepo.search(query, PageAttr.getPageRequest(page))
    }

    override fun find(id: Long): Optional<District> {
        return this.districtRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.districtRepo.deleteById(id)
            return
        }
        val district: District = this.find(id).orElseThrow { NotFoundException("Could not find district with id $id") }
        district.isDeleted = true
        this.save(district)
    }

}
