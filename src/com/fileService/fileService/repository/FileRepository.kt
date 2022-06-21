package com.fileService.fileService.repository

import com.fileService.fileService.model.FileDescriptor
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<FileDescriptor, String>