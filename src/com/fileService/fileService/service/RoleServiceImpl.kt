package com.fileService.fileService.service

import com.fileService.fileService.exception.ItemAlreadyExistsException
import com.fileService.fileService.exception.ItemNotFoundException
import com.fileService.fileService.exception.ParamNotFoundException
import com.fileService.fileService.model.Role
import com.fileService.fileService.model.RoleDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.fileService.fileService.repository.RoleRepository
import java.util.*

@Service
class RoleServiceImpl(
    @Autowired
    private val roleRepository: RoleRepository
) : RoleService {

    private val nameGenerator = { UUID.randomUUID().toString().replace("-", "") }

    private fun roleNameCheck(roleName: String) {
        getRolesList().map { role ->
            if (roleName == role.name)
                throw ItemAlreadyExistsException("Role with name: $roleName already exists, print other role name.")
        }
    }

    override fun getRoleById(id: String): Role {
        val role = roleRepository.findById(id)
        if (role.isEmpty)
            throw ItemNotFoundException("Role with id: $id isn't found.")
        else
            return role.get()
    }

    override fun getRolesList(): List<Role> {
        return roleRepository.findAll()
    }

    override fun addRole(newRole: RoleDto): Role {
        roleNameCheck(newRole.name ?: throw ParamNotFoundException("Required parameter name not found."))
        val role = Role(
            roleId = nameGenerator(),
            name = newRole.name,
            description = newRole.description
        )
        return roleRepository.save(role)
    }

    override fun updateRole(id: String, newRoleData: RoleDto): Role {
        val oldRoleData = getRoleById(id)
        if (newRoleData.name != null && newRoleData.name != oldRoleData.name) {
            roleNameCheck(newRoleData.name)
        }
        val newRole = Role(
            roleId = oldRoleData.roleId,
            description = newRoleData.description ?: oldRoleData.description,
            name = newRoleData.name ?: oldRoleData.name
        )
        return roleRepository.save(newRole)
    }

    override fun deleteRole(id: String): Boolean {
        return when (roleRepository.existsById(id)) {
            true -> {
                roleRepository.deleteById(id)
                true
            }
            false -> false
        }
    }
}