package com.fileService.fileService.service

import com.fileService.fileService.model.FileDescriptor
import com.fileService.fileService.model.FileDto
import org.springframework.core.io.InputStreamResource
import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FileService {

    fun getFile(fileId: String): File

    fun getFileById(id: String): FileDescriptor

    fun getFileList(): List<FileDescriptor>

    fun uploadFile(file: MultipartFile, userId: String): FileDescriptor

    fun downloadFile(id: String): InputStreamResource

    fun updateFile(id: String, newFile: FileDto): FileDescriptor

    fun deleteFile(id: String): Boolean

}