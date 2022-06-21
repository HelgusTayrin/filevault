package com.fileService.fileService.service

import com.fileService.fileService.exception.*
import com.fileService.fileService.model.User
import com.fileService.fileService.model.UserDto
import org.springframework.stereotype.Service
import com.fileService.fileService.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository
) : UserService {

    private val nameGenerator = { UUID.randomUUID().toString().replace("-", "") }

    private fun checkLogin(login: String) {
        getListUsers().map { user ->
            if (login == user.login)
                throw ItemAlreadyExistsException("User with login: $login already exists, print other login.")
        }
    }

    private fun validatePassword(password: String, userId: String): Boolean {
        val user = userRepository.findById(userId).get()
        return password.hashCode() == user.password.hashCode()
    }

    override fun getUserById(id: String): User {
        val user = userRepository.findById(id)
        if (user.isEmpty)
            throw ItemNotFoundException("User with id: $id isn't found.")
        else
            return user.get()
    }

    override fun getListUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun registrationUser(newUser: UserDto): User {
        checkLogin(newUser.login ?: throw ParamNotFoundException("Required parameter login not found."))
        val user = User(
            userId = nameGenerator(),
            login = newUser.login,
            password = newUser.password ?: throw ParamNotFoundException("Required parameter password not found."),
            roleId = "7ccfa63a3cb24af2b4bd9d6f74fd72a1"
        )
        return userRepository.save(user)
    }

    override fun updateUser(id: String, newUserData: UserDto, password: String): User {
        val oldUserData = getUserById(id)
        when (validatePassword(password, oldUserData.userId!!)) {
            true -> {
                if (newUserData.login != null && newUserData.login != oldUserData.login) {
                    checkLogin(newUserData.login)
                }

                val newUser = User(
                    userId = oldUserData.userId,
                    login = newUserData.login ?: oldUserData.login,
                    password = newUserData.password ?: oldUserData.password,
                    roleId = newUserData.roleId ?: oldUserData.roleId
                )
                return userRepository.save(newUser)
            }
            false -> throw NotValidPasswordException("Not valid password or token.")
        }
    }

    override fun deleteUser(id: String): Boolean {
        return when (userRepository.existsById(id)) {
            true -> {
                userRepository.deleteById(id)
                true
            }
            false -> false
        }
    }
}