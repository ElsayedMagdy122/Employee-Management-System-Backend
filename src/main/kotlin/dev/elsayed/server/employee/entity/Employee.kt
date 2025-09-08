package dev.elsayed.server.employee.entity

import java.time.LocalDate
import java.util.*

data class Employee(
    val id: UUID = UUID.randomUUID(),
    val departmentId: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val position: String,
    val hireDate: LocalDate = LocalDate.now()
)
