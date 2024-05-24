package km.self.movieticketsystem.service

import km.self.movieticketsystem.entity.Movie
import km.self.movieticketsystem.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(private val movieRepository: MovieRepository) {
    fun getMovies(): List<Movie> {
        return movieRepository.findAll();
    }
}