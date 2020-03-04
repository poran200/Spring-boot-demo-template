package com.example.webservice.domains.users.repositories

import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.domains.users.models.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findByPhone(phone: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

    @Query("SELECT u FROM User u WHERE (:q IS NULL OR (u.username LIKE %:q% OR u.name LIKE %:q%)) AND (:role IS NULL OR :role MEMBER OF u.roles)")
    fun search(@Param("q") query: String, @Param("role") role: Role?, pageable: Pageable): Page<User>

    fun findByRolesName(role: String, pageable: Pageable): Page<User>

    fun findByRolesName(role: String): List<User>


}
