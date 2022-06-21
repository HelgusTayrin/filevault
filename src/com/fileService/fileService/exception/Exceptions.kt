package com.fileService.fileService.exception

class ItemNotFoundException(message: String?) : Exception(message)
class CannotBeUpdatedException(message: String?) : Exception(message)
class ItemAlreadyExistsException(message: String?) : Exception(message)
class EntitySerializableException(message: String?) : Exception(message)
class ParamNotFoundException(message: String?): Exception(message)
class NotValidPasswordException(message: String?): Exception(message)
class EmptyListException(message: String?): Exception(message)
class FailedToCreateFileException(message: String?): Exception(message)
class FileAlreadyDeleteException(message: String?): Exception(message)