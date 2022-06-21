package com.fileService.fileService.model

import javax.persistence.*

@Entity
@Table(name = "role")
class Role(
    @Id
    @Column(name = "role_id")
    val roleId: String? = null,

    @Column(name = "role_description")
    val description: String? = null,

    @Column(name = "role_name")
    val name: String
)

class RoleDto(
    val description: String? = null,
    val name: String? = null
)