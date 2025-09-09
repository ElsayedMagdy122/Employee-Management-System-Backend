package dev.elsayed.server.employee.controller

import dev.elsayed.server.employee.dto.EmployeeCreateDto
import dev.elsayed.server.employee.dto.EmployeeUpdateDto
import dev.elsayed.server.employee.dto.toModel
import dev.elsayed.server.employee.service.EmployeeService
import dev.elsayed.server.employee.shared.GlobalResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping
    fun findAll() =
        ResponseEntity.ok(
            GlobalResponse(
                status = resolveStatus(HttpStatus.OK),
                message = "Employees retrieved successfully",
                data = employeeService.getAllEmployees()
            )
        )

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID) =
        ResponseEntity(
            GlobalResponse(
                status = resolveStatus(HttpStatus.OK),
                message = "Employee retrieved successfully",
                data = employeeService.getEmployeeById(id)
            ),
            HttpStatus.OK
        )


    @PostMapping
    fun create(@RequestBody @Valid employee: EmployeeCreateDto) = ResponseEntity(
        GlobalResponse(
            status = resolveStatus(HttpStatus.CREATED),
            message = "Employee created successfully",
            data = employeeService.createEmployee(employee.toModel())
        ),
        HttpStatus.CREATED
    )


    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody @Valid employee: EmployeeUpdateDto
    ) = ResponseEntity(
        GlobalResponse(
            status = resolveStatus(HttpStatus.OK),
            message = "Employee updated successfully",
            data = employeeService.updateEmployee(id, employee.toModel())
        ),
        HttpStatus.OK
    )


    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: UUID) = ResponseEntity(
        GlobalResponse(
            status = resolveStatus(HttpStatus.NO_CONTENT),
            message = "Employee deleted successfully",
            data = employeeService.deleteEmployee(id)
        ),
        HttpStatus.NO_CONTENT
    )

    fun resolveStatus(httpStatus: HttpStatus): String {
        return if (httpStatus.is2xxSuccessful) "success" else "error"
    }
}