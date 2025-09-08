package dev.elsayed.server.employee.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.util.*

data class Employee(
    val id: UUID = UUID.randomUUID(),
    @field:JsonProperty("department_id")
    val departmentId: UUID = UUID.randomUUID(),

    @field:NotNull("first name is required")
    @field:JsonProperty("first_name")
    val firstName: String,

    @field:NotNull("last name is required")
    @field:JsonProperty("last_name")
    val lastName: String,

    @field:NotNull("email is required")
    @field:Email(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format"
    )
    val email: String,

    @field:NotNull("phone number is required")
    @field:Pattern(
        regexp = "^\\+?[0-9]\\d{7,15}$",
        message = "Invalid phone number format"
    )
    @field:JsonProperty("phone_number")
    val phoneNumber: String,

    @field:NotNull("position is required")
    @field:Size(min = 8, max = 80, message = "min is 8 characters and max is 80 characters")
    val position: String,

    @field:PastOrPresent(message = "Hire date cannot be in the future")
    @field:JsonProperty("hire_date")
    val hireDate: LocalDate = LocalDate.now()
)
