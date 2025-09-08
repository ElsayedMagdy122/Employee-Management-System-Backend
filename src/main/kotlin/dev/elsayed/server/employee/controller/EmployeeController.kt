package dev.elsayed.server.employee.controller

import dev.elsayed.server.employee.entity.Employee
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController {
    val employees = mutableListOf<Employee>()

    @GetMapping
    fun findAll(): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(employees)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<Employee> {
        return ResponseEntity.ok(employees.find { it.id == id })
    }

    @PostMapping
    fun create(@RequestBody employee: Employee): ResponseEntity<Employee> {
        employees.add(employee)
        return ResponseEntity.ok(employee)
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: UUID): ResponseEntity<Employee> {
        employees.removeIf { it.id == id }
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody employee: Employee
    ): ResponseEntity<Employee> {
        val index = employees.indexOfFirst { it.id == id }
        return if (index != -1) {
            val updatedEmployee = employee.copy(id = id)
            employees[index] = updatedEmployee
            ResponseEntity.ok(updatedEmployee)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}