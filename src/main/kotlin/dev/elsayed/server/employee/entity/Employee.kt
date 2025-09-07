package dev.elsayed.server.employee.entity

import java.time.LocalDate
import java.util.UUID

data class Employee(
    val id : UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val hireDate: LocalDate = LocalDate.now(),
    val position: String,
    val departmentId: UUID = UUID.randomUUID(),
)
