package km.self.movieticketsystem.dto

import java.time.OffsetDateTime

data class IdentityCreationDto(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: OffsetDateTime)
