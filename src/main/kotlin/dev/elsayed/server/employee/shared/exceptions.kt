package dev.elsayed.server.employee.shared


class EmployeeNotFoundException(id: String) : RuntimeException("Employee with id=$id not found")

class EmployeeAlreadyExistsException(email: String) : RuntimeException("Employee with email=$email already exists")