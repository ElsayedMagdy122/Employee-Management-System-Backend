package dev.elsayed.server.employee.service.employee

import dev.elsayed.server.employee.entity.Employee
import dev.elsayed.server.employee.repository.EmployeeRepository
import dev.elsayed.server.employee.service.EmployeeService
import dev.elsayed.server.employee.shared.EmployeeNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class EmployeeServiceImpl(private val employeeRepository: EmployeeRepository) : EmployeeService {

    override fun getAllEmployees(): List<Employee> = employeeRepository.findAll()

    override fun getEmployeeById(id: UUID): Employee = employeeRepository.getReferenceById(id)

    override fun createEmployee(employee: Employee): Employee {
        employeeRepository.save(employee)
        return employee
    }

    override fun updateEmployee(id: UUID, employee: Employee): Employee {
        val existing = employeeRepository.findById(id)
            .orElseThrow { EmployeeNotFoundException(id.toString()) }

        val updated = existing.copy(
            firstName = employee.firstName,
            lastName = employee.lastName,
            email = employee.email,
            phoneNumber = employee.phoneNumber,
            position = employee.position,
            hireDate = employee.hireDate,
            departmentId = existing.departmentId
        )

        return employeeRepository.save(updated)
    }

    override fun deleteEmployee(id: UUID) {
        val employee = employeeRepository.findById(id)
            .orElseThrow { EmployeeNotFoundException(id.toString()) }

        employeeRepository.delete(employee)

    }
}