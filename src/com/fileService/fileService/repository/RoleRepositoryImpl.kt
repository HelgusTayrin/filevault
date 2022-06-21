//package com.fileService.fileService.repository
//
//import com.fileService.fileService.model.Role
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.jdbc.core.BeanPropertyRowMapper
//import org.springframework.jdbc.core.JdbcTemplate
//import javax.sql.DataSource
//
//class RoleRepositoryImpl(
//    val dataSource: DataSource
//) {
//
//    @Autowired
//    val jdbcTemplate = JdbcTemplate(dataSource)
//
//    fun update(role: Role){
//        val sql = ""
//        jdbcTemplate.update(sql, role)
//    }
//
//    fun query(id: String): Role {
//        val sql = "SELECT * FROM roles WHERE id = ?"
//        return jdbcTemplate.query(sql, arrayOf<Any>(id), BeanPropertyRowMapper<Role>()).stream().findAny().orElse(null)
//    }
//}