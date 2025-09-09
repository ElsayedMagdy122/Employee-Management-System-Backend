package dev.elsayed.server.employee.shared

data class GlobalResponse<T>(
    val timestamp: String = java.time.OffsetDateTime.now().toString(),
    val status: String,
    val message: String? = null,
    val errors: List<CustomError>? = null,
    val data: T? = null,

    ) {
    data class CustomError(
        val message: String? = null,
        val details: String? = null
    )
}