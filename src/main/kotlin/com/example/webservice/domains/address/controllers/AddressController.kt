package com.example.webservice.domains.address.controllers

import com.example.webservice.commons.Constants
import com.example.webservice.commons.utils.ExceptionUtil
import com.example.webservice.domains.address.models.dto.AddressDto
import com.example.webservice.domains.address.models.entities.Address
import com.example.webservice.domains.address.models.mappers.AddressMapper
import com.example.webservice.domains.address.services.AddressService
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
@Api(tags = [Constants.Swagger.ADDRESS], description = Constants.Swagger.REST_API)
class AddressController @Autowired constructor(
        private val addressService: AddressService,
        private val addressMapper: AddressMapper
) : CrudController<AddressDto> {

    @GetMapping(Route.V1.SEARCH_ADDRESSES)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.ADDRESS)
    override fun search(@RequestParam("query", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Page<AddressDto>> {
        val addresses = this.addressService.search(query, page)
        return ResponseEntity.ok(addresses.map { this.addressMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_ADDRESSES)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.ADDRESS + Constants.Swagger.BY_ID_MSG)
    override fun find(@PathVariable id: Long): ResponseEntity<AddressDto> {
        val address = this.addressService.find(id).orElseThrow { ExceptionUtil.getNotFound("Address", id) }
        return ResponseEntity.ok(this.addressMapper.map(address))
    }

    @PostMapping(Route.V1.CREATE_ADDRESSES)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.ADDRESS)
    override fun create(@Valid @RequestBody dto: AddressDto): ResponseEntity<AddressDto> {
        val address: Address = this.addressService.save(this.addressMapper.map(dto, null))
        return ResponseEntity.ok(this.addressMapper.map(address))
    }

    @PatchMapping(Route.V1.UPDATE_ADDRESSES)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.ADDRESS)
    override fun update(@PathVariable id: Long, @Valid @RequestBody dto: AddressDto): ResponseEntity<AddressDto> {
        var address: Address = addressService.find(id).orElseThrow { ExceptionUtil.getNotFound(Constants.Swagger.ADDRESS, id) }
        address = addressService.save(addressMapper.map(dto, address))
        return ResponseEntity.ok(addressMapper.map(address))
    }

    @DeleteMapping(Route.V1.DELETE_ADDRESSES)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.ADDRESS + Constants.Swagger.BY_ID_MSG)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.addressService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
