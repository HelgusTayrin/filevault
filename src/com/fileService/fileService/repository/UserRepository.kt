package com.fileService.fileService.repository

import com.fileService.fileService.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>