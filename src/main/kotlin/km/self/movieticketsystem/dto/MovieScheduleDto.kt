package km.self.movieticketsystem.dto

import km.self.movieticketsystem.entity.Movie
import km.self.movieticketsystem.entity.MovieSchedule
import java.time.OffsetDateTime

class MovieScheduleDto {
    lateinit var movie: Movie
    lateinit var schedule: OffsetDateTime
    lateinit var hall: HallDto
    lateinit var seats: MutableList<SeatDto>

    fun from(movieSchedule: MovieSchedule): MovieScheduleDto {
        this.movie = movieSchedule.movie
        this.schedule = movieSchedule.schedule
        this.hall = HallDto(movieSchedule.hall.theater, movieSchedule.hall.number)
        this.seats = ArrayList()
        movieSchedule.hall.seats.forEach { seat ->
            val isReserved = movieSchedule.reservations.find { it.seat.id == seat.id} != null
            seats.add(SeatDto(seat.row, seat.number, isReserved))
        }
        return this
    }


}