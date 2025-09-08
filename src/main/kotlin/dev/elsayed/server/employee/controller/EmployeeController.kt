package dev.elsayed.server.employee.controller

import dev.elsayed.server.employee.entity.Employee
import dev.elsayed.server.employee.shared.EmployeeAlreadyExistsException
import dev.elsayed.server.employee.shared.EmployeeNotFoundException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController {
    val employees = mutableListOf<Employee>()

    @GetMapping
    fun findAll(): ResponseEntity<List<Employee>> {
        return ResponseEntity(employees, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<Employee> {
        val employee = employees.find { it.id == id }
            ?: throw EmployeeNotFoundException(id.toString())
        return ResponseEntity(employee, HttpStatus.OK)
    }

    @PostMapping
    fun create(@RequestBody @Valid employee: Employee): ResponseEntity<Employee> {
        if (employees.any { it.email == employee.email }) {
            throw EmployeeAlreadyExistsException(employee.email)
        }
        employees.add(employee)
        return ResponseEntity(employee, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody @Valid employee: Employee): ResponseEntity<Employee> {
        val index = employees.indexOfFirst { it.id == id }
        if (index == -1) throw EmployeeNotFoundException(id.toString())

        val updatedEmployee = employee.copy(id = id, departmentId = employees[index].departmentId)
        employees[index] = updatedEmployee
        return ResponseEntity(updatedEmployee, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: UUID): ResponseEntity<Unit> {
        val removed = employees.removeIf { it.id == id }
        if (!removed) throw EmployeeNotFoundException(id.toString())
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}