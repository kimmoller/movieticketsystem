package km.self.movieticketsystem.controller

import km.self.movieticketsystem.repository.MovieRepository
import km.self.movieticketsystem.repository.MovieScheduleRepository
import km.self.movieticketsystem.service.MovieService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.time.OffsetDateTime

class MovieControllerTest : ControllerTest() {
    @Autowired
    lateinit var movieRepository: MovieRepository
    @Autowired
    lateinit var movieScheduleRepository: MovieScheduleRepository

    lateinit var movieController: MovieController

    @BeforeEach
    fun setUp() {
        val movieService = MovieService(movieRepository, movieScheduleRepository)
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

    @Test
    @Sql(statements = [
        "insert into theater(name, location) values('Bio', 'Lund')",
        "insert into hall(theater_id, number) values((select id from theater where name='Bio'), 1)",
        "insert into movie(name, runtime, description, genre) " +
                "values('comedyFilm', '1h 30m', 'First comedy film', 'Comedy')",
        "insert into movie_schedule(movie_id, schedule, hall_id) values((select id from movie where name='comedyFilm'), " +
                "'2024-05-25T15:15:00', (select id from hall where theater_id=(select id from theater where name='Bio')and number=1))"
    ])
    fun testGetMovieSchedules() {
        val schedules = movieController.getMovieSchedules(null, null, null).body
        assertEquals(1, schedules?.size)

        val schedule = schedules?.get(0)
        val hall = schedule?.hall
        val movie = schedule?.movie

        assertEquals("Bio", hall?.theater?.name)
        assertEquals("Lund", hall?.theater?.location)

        assertEquals("comedyFilm", movie?.name)

        assertEquals(OffsetDateTime.parse("2024-05-25T13:15:00Z"), schedule?.schedule)
    }

    @Test
    @Sql(statements = [
        "insert into theater(name, location) values('Bio', 'Lund')",
        "insert into hall(theater_id, number) values((select id from theater where name='Bio'), 1)",
        "insert into seat(row, number, hall_id) values(1, 1, " +
                "(select id from hall where theater_id=(select id from theater where name='Bio')and number=1))",
        "insert into movie(name, runtime, description, genre) " +
                "values('comedyFilm', '1h 30m', 'First comedy film', 'Comedy')",
        "insert into movie_schedule(movie_id, schedule, hall_id) values((select id from movie where name='comedyFilm'), " +
                "'2024-05-25T15:15:00', (select id from hall where theater_id=(select id from theater where name='Bio')and number=1))",
        "insert into movie_schedule(movie_id, schedule, hall_id) values((select id from movie where name='comedyFilm'), " +
                "'2024-05-25T17:30:00', (select id from hall where theater_id=(select id from theater where name='Bio')and number=1))"
    ])
    fun testGetMovieSchedule() {
        val schedule = movieController.getMovieSchedule(2).body
        assertEquals(OffsetDateTime.parse("2024-05-25T15:30:00Z"), schedule?.schedule)

        val seat = schedule?.seats?.get(0)
        assertEquals(1, seat?.id)
        assertEquals(1, seat?.row)
        assertEquals(1, seat?.number)
        assertEquals(false, seat?.isReserved)

        val movie = schedule?.movie
        assertEquals("comedyFilm", movie?.name)

        val hall = schedule?.hall
        assertEquals("Bio", hall?.theater?.name)
        assertEquals(1, hall?.number)
    }
}