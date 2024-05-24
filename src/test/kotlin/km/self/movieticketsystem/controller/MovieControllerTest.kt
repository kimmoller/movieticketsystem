package km.self.movieticketsystem.controller

import km.self.movieticketsystem.repository.MovieRepository
import km.self.movieticketsystem.service.MovieService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

class MovieControllerTest : ControllerTest() {
    @Autowired
    lateinit var movieRepository: MovieRepository

    lateinit var movieController: MovieController

    @BeforeEach
    fun setUp() {
        val movieService = MovieService(movieRepository)
        movieController = MovieController(movieService)
    }

    @Test
    @Sql(statements = [
        """insert into movie(name, runtime, description, genre)
            values('testMovie', '2h 30m', 'Test description', 'Comedy');"""
    ])
    fun testGetAllMovies() {
        val movies = movieController.getMovies(null)
        assertEquals(1, movies.body?.size)
        val movie = movies.body?.get(0)
        assertEquals("testMovie", movie?.name)
        assertEquals("2h 30m", movie?.runtime)
        assertEquals("Test description", movie?.description)
        assertEquals("Comedy", movie?.genre)
    }

    @Test
    @Sql(statements = [
        """insert into movie(name, runtime, description, genre)
            values('comedyFilm', '1h 30m', 'First comedy film', 'Comedy');""",
        """insert into movie(name, runtime, description, genre)
            values('comedyFilm2', '1h 40m', 'Second comedy film', 'Comedy');""",
        """insert into movie(name, runtime, description, genre)
            values('actionMovie', '2h 30m', 'Long action movie', 'Action');"""
    ])
    fun testGetMoviesFilteredByGenre() {
        val movies = movieController.getMovies("Comedy").body
        assertEquals(2, movies?.size)
        assertEquals("Comedy", movies?.get(0)?.genre)
        assertEquals("Comedy", movies?.get(1)?.genre)
    }
}