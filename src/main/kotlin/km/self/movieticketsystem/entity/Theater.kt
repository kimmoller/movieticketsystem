package km.self.movieticketsystem.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity
data class Theater(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @NotNull
    var name: String,
    @NotNull
    var location: String,
)
