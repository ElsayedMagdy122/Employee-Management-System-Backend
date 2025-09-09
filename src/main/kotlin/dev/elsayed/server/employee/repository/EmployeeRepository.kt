package dev.elsayed.server.employee.repository

import dev.elsayed.server.employee.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, UUID> {
}