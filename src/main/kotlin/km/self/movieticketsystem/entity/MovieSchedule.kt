package km.self.movieticketsystem.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.OffsetDateTime

@Entity
data class MovieSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id")
    var movie: Movie,

    @NotNull
    var schedule: OffsetDateTime,

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hall_id")
    var hall: Hall
)
