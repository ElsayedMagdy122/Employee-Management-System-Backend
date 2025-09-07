package dev.elsayed.server.employee.controller

import dev.elsayed.server.employee.entity.Employee
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController {
    val employees = listOf(
        Employee(
            firstName = "Elsayed",
            lastName = "Magdy",
            email = "email1@test.com",
            phoneNumber = "123",
            position = "Dev"
        ),
        Employee(
            firstName = "Ahmed",
            lastName = "Ali",
            email = "email2@test.com",
            phoneNumber = "456",
            position = "Tester"
        ),
    )

    @GetMapping
    fun findAll(): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(employees)
    }

    @GetMapping("/{name}")
    fun findOne(@PathVariable name: String): ResponseEntity<Employee> {
        return ResponseEntity.ok(employees.find { it.firstName.equals(name, ignoreCase = true) })
    }
}