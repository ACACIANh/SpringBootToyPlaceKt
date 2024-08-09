package com.example.opencsv.component

import com.opencsv.bean.StatefulBeanToCsv
import com.opencsv.bean.StatefulBeanToCsvBuilder
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.StandardCharsets
import kotlin.reflect.full.memberProperties

@Component
class ExcelDownloader {

    fun <T : Any> convertToExcel(data: List<T>, filename: String, response: HttpServletResponse) {
        response.contentType = "text/csv"
        response.setHeader("Content-Disposition", "attachment; filename=\"$filename.csv\"")

        val outputStream = response.outputStream
        // Write BOM for UTF-8
        outputStream.write(0xEF)
        outputStream.write(0xBB)
        outputStream.write(0xBF)

        val writer: Writer = OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
        val beanToCsv: StatefulBeanToCsv<T> = StatefulBeanToCsvBuilder<T>(writer).build()

        if (data.isNotEmpty()) {
            val headerRow = data[0]::class.java.declaredFields.joinToString(",") { it.name }
            writer.write(headerRow + "\n")
        }

        beanToCsv.write(data)
        writer.flush()
    }
}