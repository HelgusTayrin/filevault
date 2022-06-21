package com.fileService.fileService.controller

import com.fileService.fileService.model.User
import com.fileService.fileService.model.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.fileService.fileService.service.UserServiceImpl

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired
    private val userService: UserServiceImpl
) {

    @GetMapping("/getList")
    fun getUsersList(): ResponseEntity<List<User>> {
        val list = userService.getListUsers()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/get/{id}")
    fun getUser(@PathVariable(name = "id") id: String): ResponseEntity<*> {
        return try {
            val user = userService.getUserById(id)
            ResponseEntity<User>(user, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @PostMapping("/registration")
    fun addUser(@RequestBody data: UserDto): ResponseEntity<*> {
        return try {
            val user = userService.registrationUser(data)
            ResponseEntity<User>(user, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @PutMapping("/update/{id}")
    fun updateUser(
        @PathVariable(name = "id") id: String,
        @RequestBody userData: UserDto,
        @RequestParam(name = "password") password: String
    ): ResponseEntity<*> {
        return try {
            val user = userService.updateUser(id = id, newUserData = userData, password = password)
            ResponseEntity<User>(user, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Boolean> {
        return when (userService.deleteUser(id)) {
            true -> ResponseEntity<Boolean>(true, HttpStatus.OK)
            false -> ResponseEntity<Boolean>(false, HttpStatus.OK)
        }
    }
}