package km.self.movieticketsystem.service

import jakarta.transaction.Transactional
import km.self.movieticketsystem.dto.MovieScheduleDto
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
    @Transactional
    fun getMovies(genre: String?): List<Movie> {
        genre?.let {
            return movieRepository.findByGenre(genre)
        }
        return movieRepository.findAll()
    }

    @Transactional
    fun getMovieSchedules(): List<MovieSchedule> {
        return movieScheduleRepository.findAll()
    }

    @Transactional
    fun getMovieSchedule(id: Long): MovieScheduleDto {
        val movieSchedule = movieScheduleRepository.findById(id).orElseThrow()
        return MovieScheduleDto().from(movieSchedule)
    }
}