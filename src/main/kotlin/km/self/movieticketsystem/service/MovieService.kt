package km.self.movieticketsystem.service

import km.self.movieticketsystem.entity.Movie
import km.self.movieticketsystem.entity.MovieSchedule
import km.self.movieticketsystem.repository.MovieRepository
import km.self.movieticketsystem.repository.MovieScheduleRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository,
    private val movieScheduleRepository: MovieScheduleRepository
) {
    fun getMovies(genre: String?): List<Movie> {
        genre?.let {
            return movieRepository.findByGenre(genre)
        }
        return movieRepository.findAll()
    }

    fun getMovieSchedules(): List<MovieSchedule> {
        return movieScheduleRepository.findAll()
    }

    fun getMovieSchedule(id: Long): MovieSchedule {
        return movieScheduleRepository.findById(id).orElseThrow()
    }
}