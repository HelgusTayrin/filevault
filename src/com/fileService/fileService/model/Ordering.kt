package com.fileService.fileService.model

import org.springframework.data.domain.Sort

data class Ordering<R: Enum<R>>(
    val field: R,
    val direction: Sort.Direction
)

typealias RolesOrdering = List<Ordering<RoleOrderingField>>

enum class RoleOrderingField