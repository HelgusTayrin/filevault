package com.fileService.fileService.service

import com.fileService.fileService.exception.FailedToCreateFileException
import com.fileService.fileService.exception.FileAlreadyDeleteException
import com.fileService.fileService.exception.ItemNotFoundException
import com.fileService.fileService.model.FileDescriptor
import com.fileService.fileService.model.FileDto
import com.fileService.fileService.model.FileStatus
import com.fileService.fileService.repository.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.Instant
import java.util.*

@Service
class FileServiceImpl(
    @Autowired
    private val fileRepository: FileRepository
) : FileService {

    private val nameGenerator = { UUID.randomUUID().toString().replace("-", "") }

    override fun getFile(fileId: String): File {
        val fileDescriptor = getFileById(fileId)
        return File("C:\\Development\\Projects\\fileService\\resources\\uploadedFiles\\${fileDescriptor.fileFullName}")
    }

    override fun getFileById(id: String): FileDescriptor {
        val fileDescriptor = fileRepository.findById(id)
        if (fileDescriptor.isEmpty)
            throw ItemNotFoundException("File with id: $id not found.")
        else
            return fileDescriptor.get()
    }

    override fun getFileList(): List<FileDescriptor> {
        return fileRepository.findAll()
    }

    override fun uploadFile(file: MultipartFile, userId: String): FileDescriptor {
        val fileId = nameGenerator()
        val fileInStorageName = fileId+file.originalFilename
        try {
            val out = BufferedOutputStream(
                FileOutputStream(
                    File(
                        "C:\\Development\\Projects\\fileService\\resources\\uploadedFiles\\$fileInStorageName"
                    )
                )
            )
            out.write(file.bytes)
            out.flush()
            out.close()
        } catch (e: Exception) {
            throw FailedToCreateFileException("Server couldn't create file.")
        }
        return fileRepository.save(
            FileDescriptor(
                fileId = fileId,
                creationDateTime = Instant.now(),
                modifiedDateTime = null,
                deletionDateTime = null,
                fileSize = file.size,
                fileType = file.contentType!!,
                fileName = file.originalFilename!!,
                fileFullName = fileInStorageName,
                ownerId = userId,
                fileStatus = FileStatus.EXISTS
            )
        )
    }

    override fun downloadFile(id: String): InputStreamResource {
        val fileDescriptor = getFileById(id)
        val file = File("C:\\Development\\Projects\\fileService\\resources\\uploadedFiles\\${fileDescriptor.fileFullName}")
        return InputStreamResource(FileInputStream(file))
    }

    override fun updateFile(id: String, newFile: FileDto): FileDescriptor {
        val oldFileData = getFileById(id)
        val file = getFile(id)

        val fileInStorageName = oldFileData.fileId+newFile.fileName!!
        val newFileData = FileDescriptor(
            fileId = oldFileData.fileId,
            creationDateTime = oldFileData.creationDateTime,
            modifiedDateTime = Instant.now(),
            deletionDateTime = oldFileData.deletionDateTime,
            fileSize = oldFileData.fileSize,
            fileType = oldFileData.fileType,
            fileName = newFile.fileName,
            fileFullName = fileInStorageName,
            ownerId = oldFileData.ownerId,
            fileStatus = oldFileData.fileStatus
        )

        try {
            val out = BufferedOutputStream(
                FileOutputStream(
                    File(
                        "C:\\Development\\Projects\\fileService\\resources\\uploadedFiles\\$fileInStorageName"
                    )
                )
            )
            out.write(file.readBytes())
            out.flush()
            out.close()
        } catch (e: Exception) {
            throw FailedToCreateFileException("Server couldn't create file.")
        }
        file.delete()
        fileRepository.deleteById(id)
        return fileRepository.save(newFileData)
    }

    override fun deleteFile(id: String): Boolean {
        return when (fileRepository.existsById(id)) {
            true -> {
                if (getFileById(id).fileStatus != FileStatus.DELETED) {
                    getFile(id).delete()
                    getFileById(id).fileStatus = FileStatus.DELETED
                    getFileById(id).deletionDateTime = Instant.now()
                    true
                } else throw FileAlreadyDeleteException("File with id: $id already deleted.")
            }
            false -> false
        }
    }
}