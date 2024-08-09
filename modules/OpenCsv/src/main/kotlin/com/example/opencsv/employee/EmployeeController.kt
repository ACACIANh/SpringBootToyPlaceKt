package com.example.opencsv.employee

import com.example.opencsv.component.ExcelDownloader
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val excelDownloader: ExcelDownloader,
) {

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        val createdEmployee = employeeService.createEmployee(employee)
        return ResponseEntity(createdEmployee, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: Long): ResponseEntity<Employee> {
        val employee = employeeService.getEmployee(id)
        return if (employee != null) {
            ResponseEntity(employee, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAllEmployees(): ResponseEntity<List<Employee>> {
        val employees = employeeService.getAllEmployees()
        return ResponseEntity(employees, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updatedEmployee: Employee): ResponseEntity<Employee> {
        val employee = employeeService.updateEmployee(id, updatedEmployee)
        return if (employee != null) {
            ResponseEntity(employee, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<Unit> {
        val deleted = employeeService.deleteEmployee(id)
        return if (deleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/export")
    fun exportToExcel(response: HttpServletResponse) {
        val employees = employeeService.getAllEmployees()
        excelDownloader.convertToExcel(employees, "employees", response)
    }
}