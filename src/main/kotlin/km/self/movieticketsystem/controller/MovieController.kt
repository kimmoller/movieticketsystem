package km.self.movieticketsystem.controller

import km.self.movieticketsystem.entity.Movie
import km.self.movieticketsystem.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(private val movieService: MovieService) {
    @GetMapping("/movie")
    fun getMovies(): ResponseEntity<List<Movie>> {
        val movies = movieService.getMovies()
        return ResponseEntity(movies, HttpStatus.OK)
    }
}