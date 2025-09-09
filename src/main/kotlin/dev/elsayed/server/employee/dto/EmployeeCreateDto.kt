package dev.elsayed.server.employee.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.elsayed.server.employee.entity.Employee
import jakarta.validation.constraints.*
import java.time.LocalDate
import java.util.*

data class EmployeeCreateDto(
    val id: UUID = UUID.randomUUID(),
    @field:JsonProperty("department_id")
    val departmentId: UUID = UUID.randomUUID(),

    @field:NotNull(message = "first name is required")
    @field:JsonProperty("first_name")
    val firstName: String?,

    @field:NotNull(message = "last name is required")
    @field:JsonProperty("last_name")
    val lastName: String?,

    @field:NotNull(message = "email is required")
    @field:Email(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format"
    )
    val email: String?,

    @field:NotNull(message = "phone number is required")
    @field:Pattern(
        regexp = "^\\+?[0-9]\\d{7,15}$",
        message = "Invalid phone number format"
    )
    @field:JsonProperty("phone_number")
    val phoneNumber: String?,

    @field:NotNull(message = "position is required")
    @field:Size(min = 8, max = 80, message = "min is 8 characters and max is 80 characters")
    val position: String?,

    @field:PastOrPresent(message = "Hire date cannot be in the future")
    @field:JsonProperty("hire_date")
    val hireDate: LocalDate = LocalDate.now()
)

fun EmployeeCreateDto.toModel() = Employee(
    firstName = this.firstName.toString(),
    lastName = lastName.toString(),
    email = email.toString(),
    phoneNumber = phoneNumber.toString(),
    position = position.toString(),
)

fun Employee.toDto() = EmployeeCreateDto(
    id = this.id!!,
    departmentId = this.departmentId,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    phoneNumber = this.phoneNumber,
    position = this.position,
    hireDate = this.hireDate
)

