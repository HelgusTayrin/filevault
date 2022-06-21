package com.fileService.fileService.service

import com.fileService.fileService.model.User
import com.fileService.fileService.model.UserDto

interface UserService {

    fun getUserById(id: String): User

    fun getListUsers(): List<User>

    fun registrationUser(newUser: UserDto): User

    fun updateUser(id: String, newUserData: UserDto, password: String): User

    fun deleteUser(id: String): Boolean
}