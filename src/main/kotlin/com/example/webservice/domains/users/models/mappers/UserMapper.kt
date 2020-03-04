package com.example.webservice.domains.users.models.mappers

import com.example.webservice.commons.utils.PasswordUtil
import com.example.webservice.domains.users.models.dtos.UserRequest
import com.example.webservice.domains.users.models.dtos.UserResponse
import com.example.webservice.domains.users.models.entities.User
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.exceptions.exists.AlreadyExistsException
import com.example.webservice.exceptions.invalid.InvalidException
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class UserMapper @Autowired constructor(
        val userService: UserService,
        val roleService: RoleService,
        val roleMapper: RoleMapper
) {
    @Value("\${auth.method}")
    lateinit var authMethod: String

    fun map(dto: UserRequest, exUser: User?): User {
        val user = exUser ?: User()
        user.name = dto.name
        user.gender = dto.gender
        user.phone = dto.phone
        user.username = dto.username
        user.password = PasswordUtil.encryptPassword(dto.password, PasswordUtil.EncType.BCRYPT_ENCODER, null)
        user.email = dto.email
        val unrestrictedRole = this.roleService.findUnrestricted(dto.role).orElseThrow { NotFoundException("Could not find role with name ${dto.role}") }
        user.roles = listOf(unrestrictedRole)
        this.validate(user)
        return user
    }

    fun map(user: User): UserResponse {
        val dto = UserResponse()
        dto.id = user.id
        dto.created = user.created
        dto.lastUpdated = user.lastUpdated

        dto.name = user.name
        dto.gender = user.gender
        dto.username = user.username
        dto.phone = user.phone
        dto.email = user.email
        dto.roles = user.roles.stream().map { role -> role.id }.collect(Collectors.toList())
        return dto
    }

    fun validate(user: User) {
        if (user.id == null) { // For new user
            if (this.userService.findByUsername(user.username).isPresent) throw AlreadyExistsException("User already exists with username: ${user.username}")
            if (authMethod == "phone") {
                if (user.phone == null || user.phone.isEmpty()) throw InvalidException("Phone number can't be null or empty!")
                if (this.userService.findByPhone(user.phone).isPresent) throw AlreadyExistsException("User already exists with phone: ${user.phone}")
            } else {
                if (user.email == null || user.email.isEmpty()) throw InvalidException("Email can't be null or empty!")
                if (this.userService.findByEmail(user.email).isPresent) throw AlreadyExistsException("User already exists with email: ${user.email}")

            }
        }
    }
}
