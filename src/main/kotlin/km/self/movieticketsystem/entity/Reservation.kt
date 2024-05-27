package km.self.movieticketsystem.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import org.jetbrains.annotations.NotNull

@Entity
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @NotNull
    @ManyToOne
    var identity: Identity,

    @NotNull
    @ManyToOne
    var movieSchedule: MovieSchedule,

    @NotNull
    @OneToOne(mappedBy = "")
    var seat: Seat
)
