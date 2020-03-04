package com.example.webservice.domains.address.controllers

import com.example.webservice.commons.Constants
import com.example.webservice.commons.utils.ExceptionUtil
import com.example.webservice.domains.address.models.dto.DivisionDto
import com.example.webservice.domains.address.models.entities.Division
import com.example.webservice.domains.address.models.mappers.DivisionMapper
import com.example.webservice.domains.address.services.DivisionService
import com.example.webservice.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = [Constants.Swagger.DIVISION], description = Constants.Swagger.REST_API)
class DivisionController(
        @Autowired val divisionService: DivisionService,
        @Autowired val divisionMapper: DivisionMapper
) {

    @GetMapping(Route.V1.SEARCH_DIVISION)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.DIVISION)
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any> {
        val divisions: Page<Division> = this.divisionService.search(query, page)
        return ResponseEntity.ok(divisions.map { division -> this.divisionMapper.map(division) })
    }

    @GetMapping(Route.V1.FIND_DIVISION)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.DIVISION)
    fun find(@PathVariable id: Long): ResponseEntity<Any> {
        val division: Division = this.divisionService.find(id).orElseThrow { ExceptionUtil.getNotFound("division", id) }
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @PostMapping(Route.V1.CREATE_DIVISION)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.DIVISION)
    fun save(@Valid @RequestBody divisionDto: DivisionDto): ResponseEntity<Any> {
        val division: Division = this.divisionService.save(this.divisionMapper.map(divisionDto, null))
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @PatchMapping(Route.V1.UPDATE_DIVISION)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.DIVISION)
    fun update(@Valid @RequestBody divisionDto: DivisionDto, @PathVariable id: Long): ResponseEntity<Any> {
        var division: Division = this.divisionService.find(id).orElseThrow { ExceptionUtil.getNotFound("division", id) }
        division = this.divisionService.save(this.divisionMapper.map(divisionDto, division))
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @DeleteMapping(Route.V1.DELETE_DIVISION)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.DIVISION)
    fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.divisionService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
