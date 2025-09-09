package dev.elsayed.server.employee.shared

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class GlobalExceptionResponse {

    @ExceptionHandler(EmployeeNotFoundException::class)
    fun handleEmployeeNotFound(
        e: EmployeeNotFoundException,
        request: HttpServletRequest
    ) = buildResponse(
        HttpStatus.NOT_FOUND,
        "Resource not found",
        listOf(GlobalResponse.CustomError(message = e.message ?: "Employee not found"))
    )

    @ExceptionHandler(EmployeeAlreadyExistsException::class)
    fun handleEmployeeAlreadyExists(
        e: EmployeeAlreadyExistsException,
        request: HttpServletRequest
    ) = buildResponse(
        HttpStatus.CONFLICT,
        "Conflict",
        listOf(GlobalResponse.CustomError(message = e.message ?: "Conflict error"))
    )

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(
        e: NoResourceFoundException,
        request: HttpServletRequest
    ) = buildResponse(
        HttpStatus.NOT_FOUND,
        "Resource not found",
        listOf(GlobalResponse.CustomError(message = "The requested resource was not found"))
    )

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        e: ConstraintViolationException,
        request: HttpServletRequest
    ) = buildResponse(
        HttpStatus.BAD_REQUEST,
        "Validation failed",
        e.constraintViolations.map {
            GlobalResponse.CustomError(
                message = it.message,
                details = "Field: ${it.propertyPath}, Invalid value: ${it.invalidValue}"
            )
        }
    )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<GlobalResponse<Unit>> {
        val rootCause = e.rootCause?.message ?: e.message ?: "Invalid request body"

        val errors = listOf(
            GlobalResponse.CustomError(
                message = "Invalid JSON format",
                details = rootCause
            )
        )

        return ResponseEntity(
            GlobalResponse(
                status = resolveStatus(HttpStatus.BAD_REQUEST),
                message = "Validation failed",
                errors = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ) = buildResponse(
        HttpStatus.BAD_REQUEST,
        "Validation failed",
        e.bindingResult.fieldErrors.map {
            GlobalResponse.CustomError(
                message = it.defaultMessage ?: "Validation error",
                details = "Field: ${it.field}, Rejected value: ${it.rejectedValue}"
            )
        }
    )

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        e: Exception,
        request: HttpServletRequest
    ) = buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal Server Error",
        listOf(
            GlobalResponse.CustomError(
                message = e.message ?: "Unexpected error occurred",
                details = e.javaClass.simpleName
            )
        )
    )

    private fun buildResponse(
        status: HttpStatus,
        message: String,
        errors: List<GlobalResponse.CustomError>
    ): ResponseEntity<GlobalResponse<Unit>> {
        return ResponseEntity(
            GlobalResponse(
                status = resolveStatus(status),
                message = message,
                errors = errors
            ),
            status
        )
    }

    fun resolveStatus(httpStatus: HttpStatus): String {
        return if (httpStatus.is2xxSuccessful) "success" else "error"
    }
}

