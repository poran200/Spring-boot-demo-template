package com.example.webservice.domains.address.controllers

import com.example.webservice.commons.Constants
import com.example.webservice.commons.utils.ExceptionUtil
import com.example.webservice.domains.address.models.dto.UpazilaDto
import com.example.webservice.domains.address.models.entities.Upazila
import com.example.webservice.domains.address.models.mappers.UpazilaMapper
import com.example.webservice.domains.address.services.UpazilaService
import com.example.webservice.domains.common.controller.CrudController
import com.example.webservice.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = [Constants.Swagger.UPAZILA], description = Constants.Swagger.REST_API)
class UpazilaController @Autowired constructor(
        private val upazilaService: UpazilaService,
        private val upazilaMapper: UpazilaMapper
) : CrudController<UpazilaDto> {

    @GetMapping(Route.V1.SEARCH_UPAZILA)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.UPAZILA)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Page<UpazilaDto>> {
        val upazilas: Page<Upazila> = upazilaService.search(query, page)
        return ResponseEntity.ok(upazilas.map { upazila -> upazilaMapper.map(upazila) })
    }

    @GetMapping(Route.V1.FIND_UPAZILA)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.UPAZILA)
    override fun find(@PathVariable id: Long): ResponseEntity<UpazilaDto> {
        val upazila: Upazila = upazilaService.find(id).orElseThrow { ExceptionUtil.getNotFound("upazila", id) }
        return ResponseEntity.ok(upazilaMapper.map(upazila))
    }

    @PostMapping(Route.V1.CREATE_UPAZILA)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.UPAZILA)
    override fun create(@Valid @RequestBody dto: UpazilaDto): ResponseEntity<UpazilaDto> {
        var upazila = upazilaMapper.map(dto, null)
        upazila = upazilaService.save(upazila)
        return ResponseEntity.ok(upazilaMapper.map(upazila))
    }

    @PatchMapping(Route.V1.UPDATE_UPAZILA)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.UPAZILA)
    override fun update(@PathVariable id: Long, @Valid @RequestBody dto: UpazilaDto): ResponseEntity<UpazilaDto> {
        var upazila: Upazila = upazilaService.find(id).orElseThrow { ExceptionUtil.getNotFound("upazila", id) }
        upazila = upazilaService.save(upazilaMapper.map(dto, upazila))
        return ResponseEntity.ok(upazilaMapper.map(upazila))
    }

    @DeleteMapping(Route.V1.DELETE_UPAZILA)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.UPAZILA)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.upazilaService.delete(id, true)
        return ResponseEntity.ok().build()
    }
}
