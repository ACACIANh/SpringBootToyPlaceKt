package com.example.opencsv.employee

import org.springframework.stereotype.Service

@Service
class EmployeeService {
    private val employees = mutableListOf<Employee>()
    private var nextId: Long = 1

    fun createEmployee(employee: Employee): Employee {
        val newEmployee = employee.copy(id = nextId++)
        employees.add(newEmployee)
        return newEmployee
    }

    fun getEmployee(id: Long): Employee? {
        return employees.find { it.id == id }
    }

    fun getAllEmployees(): List<Employee> {
        return employees.toList()
    }

    fun updateEmployee(id: Long, updatedEmployee: Employee): Employee? {
        val index = employees.indexOfFirst { it.id == id }
        if (index != -1) {
            val employee = updatedEmployee.copy(id = id)
            employees[index] = employee
            return employee
        }
        return null
    }

    fun deleteEmployee(id: Long): Boolean {
        return employees.removeIf { it.id == id }
    }
}