package km.self.movieticketsystem.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull
import java.time.OffsetDateTime

@Entity
data class Identity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @NotNull
    var username: String,
    @NotNull
    var password: String,
    @NotNull
    var email: String,
    @NotNull
    var dateOfBirth: OffsetDateTime,
    @NotNull
    var firstName: String,
    @NotNull
    var lastName: String
)
