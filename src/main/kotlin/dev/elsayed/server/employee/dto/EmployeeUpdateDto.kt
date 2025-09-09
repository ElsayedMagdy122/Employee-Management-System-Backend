package dev.elsayed.server.employee.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.elsayed.server.employee.entity.Employee
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class EmployeeUpdateDto(
    @field:NotNull(message = "first name is required")
    @field:JsonProperty("first_name")
    val firstName: String?,

    @field:NotNull(message = "last name is required")
    @field:JsonProperty("last_name")
    val lastName: String?,

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
)

fun EmployeeUpdateDto.toModel() = Employee(
    firstName = this.firstName.toString(),
    lastName = lastName.toString(),
    email = "",
    phoneNumber = phoneNumber.toString(),
    position = position.toString(),
)

fun Employee.toUpdateDto() = EmployeeUpdateDto(
    firstName = this.firstName,
    lastName = this.lastName,
    phoneNumber = this.phoneNumber,
    position = this.position,
)