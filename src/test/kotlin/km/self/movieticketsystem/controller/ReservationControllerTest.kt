package km.self.movieticketsystem.controller

import km.self.movieticketsystem.dto.ReservationDto
import km.self.movieticketsystem.entity.*
import km.self.movieticketsystem.repository.IdentityRepository
import km.self.movieticketsystem.repository.MovieScheduleRepository
import km.self.movieticketsystem.repository.ReservationRepository
import km.self.movieticketsystem.repository.SeatRepository
import km.self.movieticketsystem.service.ReservationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime
import java.util.*

class ReservationControllerTest {
    @Mock
    lateinit var identityRepository: IdentityRepository
    @Mock
    lateinit var movieScheduleRepository: MovieScheduleRepository
    @Mock
    lateinit var seatRepository: SeatRepository
    @Mock
    lateinit var reservationRepository: ReservationRepository

    lateinit var reservationController: ReservationController

    @BeforeEach
    fun setUp() {
        identityRepository = Mockito.mock(IdentityRepository::class.java)
        movieScheduleRepository = Mockito.mock(MovieScheduleRepository::class.java)
        seatRepository = Mockito.mock(SeatRepository::class.java)
        reservationRepository = Mockito.mock(ReservationRepository::class.java)
        val reservationService = ReservationService(
            identityRepository,
            movieScheduleRepository,
            seatRepository,
            reservationRepository)

        reservationController = ReservationController(reservationService)
    }

    @Test
    fun testReserveOneTicket() {
        mockBaseData()
        val reservationDto = ReservationDto(1, 1, listOf(1))
        val response = reservationController.reserveTickets(reservationDto)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun testReserveTwoTickets() {
        mockBaseData()
        val reservationDto = ReservationDto(1, 1, listOf(1, 2))
        val response = reservationController.reserveTickets(reservationDto)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun testReserveTickets_whenScheduleNotFound_return404() {
        val reservationDto = ReservationDto(1, 1, listOf(1))
        val response = reservationController.reserveTickets(reservationDto)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    private fun mockBaseData() {
        val identity = Identity(1, "username", "password", "email@test.com",
            OffsetDateTime.parse("1993-01-01T00:00:00Z"), "Test", "User")
        Mockito.`when`(identityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(identity))

        val theater = Theater(1, "Theater", "Lund")

        val seat = Seat(1, 1, 1)
        Mockito.`when`(seatRepository.findById(1)).thenReturn(Optional.of(seat))
        val seat2 = Seat(2,1,2)
        Mockito.`when`(seatRepository.findById(2)).thenReturn(Optional.of(seat2))
        val seats = listOf(seat, seat2)
        val hall = Hall(1, theater, 1, seats)

        val movie = Movie(1, "Comedy Film", "1h30m", "A comedy film", "Comedy")
        val movieSchedule = MovieSchedule(1, movie, OffsetDateTime.parse("2024-06-15T12:00:00Z"), hall, listOf())
        Mockito.`when`(movieScheduleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(movieSchedule))
    }
}