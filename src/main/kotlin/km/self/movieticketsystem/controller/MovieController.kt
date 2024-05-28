package km.self.movieticketsystem.controller

import jakarta.websocket.server.PathParam
import km.self.movieticketsystem.dto.MovieScheduleDto
import km.self.movieticketsystem.dto.ReservationDto
import km.self.movieticketsystem.entity.Movie
import km.self.movieticketsystem.entity.MovieSchedule
import km.self.movieticketsystem.entity.Reservation
import km.self.movieticketsystem.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
class MovieController(private val movieService: MovieService) {
    @GetMapping("/movie")
    fun getMovies(@RequestParam genre: String?): ResponseEntity<List<Movie>> {
        val movies = movieService.getMovies(genre)
        return ResponseEntity(movies, HttpStatus.OK)
    }

    @GetMapping("/movie/schedule")
    fun getMovieSchedules(
        @RequestParam theater: Long?,
        @RequestParam day: OffsetDateTime?,
        @RequestParam movie: Long?
    ): ResponseEntity<List<MovieSchedule>> {
        val schedules = movieService.getMovieSchedules()
        return ResponseEntity(schedules, HttpStatus.OK)
    }

    @GetMapping("/movie/schedule/:id")
    fun getMovieSchedule(@PathParam("id") id: Long): ResponseEntity<MovieScheduleDto> {
        val schedule = movieService.getMovieSchedule(id)
        return ResponseEntity(schedule, HttpStatus.OK)
    }
}