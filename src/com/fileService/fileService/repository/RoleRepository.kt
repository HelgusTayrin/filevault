package com.fileService.fileService.repository

import com.fileService.fileService.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String>