package km.self.movieticketsystem.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Hall(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @NotNull
    @ManyToOne
    var theater: Theater,

    var number: Number
)
