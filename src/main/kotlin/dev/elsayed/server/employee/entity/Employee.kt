package dev.elsayed.server.employee.entity

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    val id: UUID? = null,
    @Column(name = "department_id")
    val departmentId: UUID = UUID.randomUUID(),
    @Column(name = "first_name", nullable = false, length = 100)
    val firstName: String,
    @Column(name = "last_name", nullable = false, length = 100)
    val lastName: String,
    @Column(name = "email", nullable = false, length = 50, unique = true)
    val email: String,
    @Column(name = "phone_number", nullable = false, length = 20)
    val phoneNumber: String,
    @Column(name = "position", nullable = false, length = 50)
    val position: String,
    @Column(name = "hire_date")
    val hireDate: LocalDate = LocalDate.now()
)
