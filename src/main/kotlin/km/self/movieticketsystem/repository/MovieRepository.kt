package km.self.movieticketsystem.repository

import km.self.movieticketsystem.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, Long?> {
    fun findByGenre(genre: String): List<Movie>
}