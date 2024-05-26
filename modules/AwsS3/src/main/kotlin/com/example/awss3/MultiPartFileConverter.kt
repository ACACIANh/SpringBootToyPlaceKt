package com.example.awss3

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class MultiPartFileConverter {

    companion object {
        //MediaType 대신 String 으로 사용..
        @Throws(IOException::class)
        fun convert(file: File, contentType: String, fileName: String): MultipartFile {
            FileInputStream(file).use { input ->
                return MockMultipartFile(fileName, file.name, contentType, input)
            }
        }
    }
}