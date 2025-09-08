package dev.elsayed.server.employee.shared

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<GlobalResponse<Unit>> {
        return ResponseEntity(
            GlobalResponse(
                status = HttpStatus.NOT_FOUND.value(),
                message = HttpStatus.NOT_FOUND.reasonPhrase,
                error = GlobalResponse.CustomError(
                    message = e.message ?: "Employee not found"
                )
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(EmployeeAlreadyExistsException::class)
    fun handleEmployeeAlreadyExists(
        e: EmployeeAlreadyExistsException,
        request: HttpServletRequest
    ): ResponseEntity<GlobalResponse<Unit>> {
        return ResponseEntity(
            GlobalResponse(
                status = HttpStatus.CONFLICT.value(),
                message = HttpStatus.CONFLICT.reasonPhrase,
                error = GlobalResponse.CustomError(
                    message = e.message ?: "Conflict error"
                )
            ),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(
        e: NoResourceFoundException,
        request: HttpServletRequest
    ): ResponseEntity<GlobalResponse<Unit>> {
        return ResponseEntity(
            GlobalResponse(
                status = HttpStatus.NOT_FOUND.value(),
                message = HttpStatus.NOT_FOUND.reasonPhrase,
                error = GlobalResponse.CustomError(
                    message = "The requested resource was not found"
                )
            ),
            HttpStatus.NOT_FOUND
        )
    }

    // handle validation errors from @Valid @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<GlobalResponse<Unit>> {
        val errors = e.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }

        return ResponseEntity(
            GlobalResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = HttpStatus.BAD_REQUEST.reasonPhrase,
                error = GlobalResponse.CustomError(
                    message = "Validation failed",
                    details = errors.toString()
                )
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    //  handle validation errors from @Validated on params/path
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        e: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<GlobalResponse<Unit>> {
        val errors = e.constraintViolations.associate { it.propertyPath.toString() to it.message }

        return ResponseEntity(
            GlobalResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = HttpStatus.BAD_REQUEST.reasonPhrase,
                error = GlobalResponse.CustomError(
                    message = "Validation failed",
                    details = errors.toString()
                )
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}

