package dev.elsayed.server.employee.shared

data class GlobalResponse<T>(
    val timestamp: String = java.time.OffsetDateTime.now().toString(),
    val status: Int,
    val message: String? = null,
    val data: T? = null,
    val error: CustomError? = null
) {
    data class CustomError(
        val message: String,
        val details: String? = null
    )
}