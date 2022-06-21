package com.fileService.fileService.controller

import com.fileService.fileService.model.Role
import com.fileService.fileService.model.RoleDto
import com.fileService.fileService.service.RoleServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/roles")
class RolesController(
    @Autowired
    private val rolesService: RoleServiceImpl
) {
    @GetMapping("/getList")
    fun getRolesList(): ResponseEntity<List<Role>> {
        val list = rolesService.getRolesList()
        return ResponseEntity<List<Role>>(list, HttpStatus.OK)
    }

    @GetMapping("/get/{id}")
    fun getRoleById(@PathVariable(name = "id") id: String): ResponseEntity<*> {
        return try {
            val role = rolesService.getRoleById(id)
            ResponseEntity<Role>(role, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @PostMapping("/add")
    fun addRole(@RequestBody data: RoleDto): ResponseEntity<*> {
        return try {
            val role = rolesService.addRole(data)
            ResponseEntity<Role>(role, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @PutMapping("/update/{id}")
    fun updateRole(@PathVariable id: String, @RequestBody roleData: RoleDto): ResponseEntity<*> {
        return try {
            val role = rolesService.updateRole(id = id, newRoleData = roleData)
            ResponseEntity<Role>(role, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteRole(@PathVariable(name = "id") id: String): ResponseEntity<Boolean> {
        return when (rolesService.deleteRole(id)) {
            true -> ResponseEntity<Boolean>(true, HttpStatus.OK)
            false -> ResponseEntity<Boolean>(false, HttpStatus.OK)
        }
    }
}