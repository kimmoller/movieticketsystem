package km.self.movieticketsystem.controller

import km.self.movieticketsystem.entity.*
import km.self.movieticketsystem.repository.MovieRepository
import km.self.movieticketsystem.repository.MovieScheduleRepository
import km.self.movieticketsystem.service.MovieService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime
import java.util.*

class MovieControllerTest {
    @Mock
    lateinit var movieRepository: MovieRepository
    @Mock
    lateinit var movieScheduleRepository: MovieScheduleRepository

    lateinit var movieController: MovieController

    @BeforeEach
    fun setUp() {
        movieRepository = Mockito.mock(MovieRepository::class.java)
        movieScheduleRepository = Mockito.mock(MovieScheduleRepository::class.java)
        val movieService = MovieService(movieRepository, movieScheduleRepository)
        movieController = MovieController(movieService)
    }

    @Test
    fun testGetAllMovies() {
        val mockMovie = Movie(1, "Action Film", "1h 30m", "An action film", "Action")
        Mockito.`when`(movieRepository.findAll()).thenReturn(listOf(mockMovie))
        val movies = movieController.getMovies(null)
        assertEquals(1, movies.body?.size)
        val movie = movies.body?.get(0)
        assertEquals("Action Film", movie?.name)
        assertEquals("1h 30m", movie?.runtime)
        assertEquals("An action film", movie?.description)
        assertEquals("Action", movie?.genre)
    }

    @Test
    fun testGetMovies_whenNoMovies_returnsEmptyList() {
        val movies = movieController.getMovies(null).body
        assertEquals(0, movies?.size)
    }

    @Test
    fun testGetMoviesFilteredByGenre() {
        val movie = Movie(1, "Action Film", "1h30m", "An action film", "Action")
        Mockito.`when`(movieRepository.findByGenre("Action")).thenReturn(listOf(movie))
        val movies = movieController.getMovies("Action").body
        assertEquals(1, movies?.size)
        assertEquals("Action", movies?.get(0)?.genre)
    }

    @Test
    fun testGetMovieSchedules() {
        mockBaseData()
        val schedules = movieController.getMovieSchedules(null, null, null).body
        assertEquals(1, schedules?.size)

        val schedule = schedules?.get(0)
        val hall = schedule?.hall
        val movie = schedule?.movie

        assertEquals("Theater", hall?.theater?.name)
        assertEquals("Lund", hall?.theater?.location)

        assertEquals("Comedy Film", movie?.name)

        assertEquals(OffsetDateTime.parse("2024-06-15T12:00:00Z"), schedule?.schedule)
    }

    @Test
    fun testGetMovieSchedules_whenNoSchedulesFound_returnsEmptyList() {
        val schedules = movieController.getMovieSchedules(null, null, null).body
        assertEquals(0, schedules?.size)
    }

    @Test
    fun testGetMovieSchedule() {
        mockBaseData()
        val schedule = movieController.getMovieSchedule(1).body
        assertEquals(OffsetDateTime.parse("2024-06-15T12:00:00Z"), schedule?.schedule)

        val seat = schedule?.seats?.get(0)
        assertEquals(1, seat?.id)
        assertEquals(1, seat?.row)
        assertEquals(1, seat?.number)
        assertEquals(false, seat?.isReserved)

        val movie = schedule?.movie
        assertEquals("Comedy Film", movie?.name)

        val hall = schedule?.hall
        assertEquals("Theater", hall?.theater?.name)
        assertEquals(1, hall?.number)
    }

    @Test
    fun testGetMovieSchedule_whenScheduleNotFound_throwError404() {
        val response = movieController.getMovieSchedule(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    private fun mockBaseData() {
        val theater = Theater(1, "Theater", "Lund")

        val seat = Seat(1, 1, 1)
        val seats = listOf(seat)
        val hall = Hall(1, theater, 1, seats)

        val movie = Movie(1, "Comedy Film", "1h30m", "A comedy film", "Comedy")
        val movieSchedule = MovieSchedule(1, movie, OffsetDateTime.parse("2024-06-15T12:00:00Z"), hall, listOf())
        Mockito.`when`(movieScheduleRepository.findAll()).thenReturn(listOf(movieSchedule))
        Mockito.`when`(movieScheduleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(movieSchedule))
    }
}