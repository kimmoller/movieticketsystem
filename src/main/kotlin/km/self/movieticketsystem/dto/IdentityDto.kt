package km.self.movieticketsystem.dto

import java.time.OffsetDateTime

data class IdentityDto(
    val username: String,
    val email: String,
    val dateOfBirth: OffsetDateTime,
    val firstName: String,
    val lastName: String
)
