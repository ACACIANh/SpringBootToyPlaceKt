package com.example.opencsv

import com.example.opencsv.component.ExcelDownloader
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SimpleController(
    private val excelDownloader: ExcelDownloader,
) {
    @GetMapping("/simple-export")
    fun exportToExcel(response: HttpServletResponse) {
        val data = listOf(
            SimpleDTO("John", 30),
            SimpleDTO("Jane", 25)
        )
        excelDownloader.convertToExcel(data, "sample_data", response)
    }

    data class SimpleDTO(val name: String, val age: Int)
}

