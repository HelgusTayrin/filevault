package com.fileService.fileService.service

import com.fileService.fileService.model.Role
import com.fileService.fileService.model.RoleDto

interface RoleService {

    fun getRoleById(id: String): Role

    fun getRolesList(): List<Role>

    fun addRole(newRole: RoleDto): Role

    fun updateRole(id: String, newRoleData: RoleDto): Role

    fun deleteRole(id: String): Boolean

}