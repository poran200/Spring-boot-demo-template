package com.example.webservice.domains.address.models.entities

import com.example.webservice.domains.common.models.entities.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "addr_upazilas")
class Upazila : BaseEntity() {
    @Column(name = "name_en")
    var nameEn: String? = null

    @Column(name = "name_bn")
    var nameBn: String? = null

    @ManyToOne
    var district: District? = null
}

