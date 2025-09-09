package dev.elsayed.server.employee.service

import dev.elsayed.server.employee.entity.Employee
import java.util.*

interface EmployeeService {
    fun getAllEmployees(): List<Employee>
    fun getEmployeeById(id: UUID): Employee
    fun createEmployee(employee: Employee): Employee
    fun updateEmployee(id: UUID, employee: Employee): Employee
    fun deleteEmployee(id: UUID)
}