package com.fileService.fileService.model

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "file_descriptor")
class FileDescriptor(
    @Id
    @Column(name = "file_id")
    val fileId: String? = null,

    @Column(name = "created_datetime")
    val creationDateTime: Instant,

    @Column(name = "modified_datetime")
    var modifiedDateTime: Instant? = null,

    @Column(name = "deleted_datetime")
    var deletionDateTime: Instant? = null,

    @Column(name = "file_size")
    var fileSize: Long,

    @Column(name = "file_type")
    var fileType: String,

    @Column(name = "file_name")
    var fileName: String,

    @Column(name = "file_fullname")
    val fileFullName: String,

    @Column(name = "owner_id")
    val ownerId: String,

    @Column(name = "file_status")
    var fileStatus: FileStatus
)

class FileDto(
    val fileName: String? = null
)