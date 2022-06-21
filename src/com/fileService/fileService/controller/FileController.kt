package com.fileService.fileService.controller

import com.fileService.fileService.model.FileDescriptor
import com.fileService.fileService.model.FileDto
import com.fileService.fileService.service.FileServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.ServletContext

@RestController
@RequestMapping("/files")
class FileController(
    @Autowired
    private val fileService: FileServiceImpl,
    private val servletContext: ServletContext
) {

    private fun getMediaTypeForFileId(context: ServletContext, id: String): MediaType {
        val file = fileService.getFileById(id)
        val mimeType: String = context.getMimeType(file.fileFullName)
        return try {
            MediaType.parseMediaType(mimeType)
        } catch (e: Exception) {
            MediaType.APPLICATION_OCTET_STREAM
        }
    }

    @GetMapping("/getList")
    fun getFilesList(): ResponseEntity<List<FileDescriptor>> {
        val list = fileService.getFileList()
        return ResponseEntity<List<FileDescriptor>>(list, HttpStatus.OK)
    }

    @GetMapping("/get/{id}")
    fun getFile(@PathVariable(name = "id") id: String): ResponseEntity<*> {
        return try {
            val fileDescriptor = fileService.getFileById(id)
            ResponseEntity<FileDescriptor>(fileDescriptor, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @GetMapping("/download/{id}")
    fun downloadFile(@PathVariable(name = "id") id: String): ResponseEntity<InputStreamResource> {
        val mediaType: MediaType = getMediaTypeForFileId(this.servletContext, id)
        val resource = fileService.downloadFile(id)
        val file = fileService.getFile(id)
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.name).contentType(mediaType).contentLength(file.length()).body(resource)
    }

    @PostMapping("/upload/{userId}")
    fun uploadFile(@RequestPart(name = "file") file: MultipartFile, @PathVariable(name = "userId") userId: String):ResponseEntity<*> {
        return try {
            val fileDescriptor = fileService.uploadFile(file, userId)
            ResponseEntity<FileDescriptor>(fileDescriptor, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @PutMapping("/update/{id}")
    fun updateFile(@PathVariable id: String, @RequestBody file: FileDto): ResponseEntity<*> {
        return try {
            val updatedFile = fileService.updateFile(id = id, newFile = file)
            ResponseEntity<FileDescriptor>(updatedFile, HttpStatus.CREATED)
        } catch(e: Exception) {
            ResponseEntity(e, HttpStatus.OK)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteFile(@PathVariable(name = "id") id: String): ResponseEntity<Boolean> {
        return when(fileService.deleteFile(id)) {
          true -> ResponseEntity<Boolean>(true, HttpStatus.OK)
          false -> ResponseEntity<Boolean>(false, HttpStatus.OK)
        }
    }
}