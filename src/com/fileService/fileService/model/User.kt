package com.fileService.fileService.model

import javax.persistence.*

@Entity
@Table(name = "usertable")
class User(
    @Id
    @Column(name = "id")
    val userId: String? = null,

    @Column(name = "login")
    val login: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "role")
    val roleId: String
)

class UserDto(
    val login: String? = null,
    val password: String? = null,
    val roleId: String? = null
)