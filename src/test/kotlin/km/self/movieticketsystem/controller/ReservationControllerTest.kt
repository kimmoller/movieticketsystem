package km.self.movieticketsystem.controller

import km.self.movieticketsystem.dto.ReservationDto
import km.self.movieticketsystem.repository.IdentityRepository
import km.self.movieticketsystem.repository.MovieScheduleRepository
import km.self.movieticketsystem.repository.ReservationRepository
import km.self.movieticketsystem.repository.SeatRepository
import km.self.movieticketsystem.service.ReservationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import java.time.OffsetDateTime

class ReservationControllerTest: ControllerTest() {
    @Autowired
    lateinit var identityRepository: IdentityRepository
    @Autowired
    lateinit var movieScheduleRepository: MovieScheduleRepository
    @Autowired
    lateinit var seatRepository: SeatRepository
    @Autowired
    lateinit var reservationRepository: ReservationRepository

    lateinit var reservationController: ReservationController

    @BeforeEach
    fun setUp() {
        val reservationService = ReservationService(
            identityRepository,
            movieScheduleRepository,
            seatRepository,
            reservationRepository)

        reservationController = ReservationController(reservationService)
    }

    @Test
    @Sql(statements = [
        "insert into identity(username, password, email, date_of_birth, first_name, last_name)" +
                " values('testUser', 'testPass', 'test@example.net', '1993-01-01T00:00:00Z', 'Test', 'User')",
        "insert into theater(name, location) values('Bio', 'Lund')",
        "insert into hall(theater_id, number) values((select id from theater where name='Bio'), 1)",
        "insert into seat(row, number, hall_id) values(1, 1, " +
                "(select id from hall where theater_id=(select id from theater where name='Bio')and number=1))",
        "insert into movie(name, runtime, description, genre) " +
                "values('comedyFilm', '1h 30m', 'First comedy film', 'Comedy')",
        "insert into movie_schedule(movie_id, schedule, hall_id) values((select id from movie where name='comedyFilm'), " +
                "'2024-05-25T15:15:00', (select id from hall where theater_id=(select id from theater where name='Bio')and number=1))"
    ])
    fun testReserveOneTicket() {
        val reservationDto = ReservationDto(1, 1, listOf(1))
        val response = reservationController.reserveTickets(reservationDto)
        assertEquals(HttpStatus.OK, response.statusCode)

        val reservation = reservationRepository.findById(1).orElseThrow()
        assertEquals("Test", reservation?.identity?.firstName)
        assertEquals(OffsetDateTime.parse("2024-05-25T13:15:00Z"), reservation?.movieSchedule?.schedule)
        assertEquals(1, reservation?.seat?.row)
        assertEquals(1, reservation?.seat?.number)
    }

    @Test
    @Sql(statements = [
        "insert into identity(username, password, email, date_of_birth, first_name, last_name)" +
                " values('testUser', 'testPass', 'test@example.net', '1993-01-01T00:00:00Z', 'Test', 'User')",
        "insert into theater(name, location) values('Bio', 'Lund')",
        "insert into hall(theater_id, number) values((select id from theater where name='Bio'), 1)",
        "insert into seat(row, number, hall_id) values(1, 1, " +
                "(select id from hall where theater_id=(select id from theater where name='Bio')and number=1))",
        "insert into seat(row, number, hall_id) values(1, 2, " +
                "(select id from hall where theater_id=(select id from theater where name='Bio')and number=1))",
        "insert into movie(name, runtime, description, genre) " +
                "values('comedyFilm', '1h 30m', 'First comedy film', 'Comedy')",
        "insert into movie_schedule(movie_id, schedule, hall_id) values((select id from movie where name='comedyFilm'), " +
                "'2024-05-25T15:15:00', (select id from hall where theater_id=(select id from theater where name='Bio')and number=1))"
    ])
    fun testReserveTwoTickets() {
        val reservationDto = ReservationDto(1, 1, listOf(1, 2))
        val response = reservationController.reserveTickets(reservationDto)
        assertEquals(HttpStatus.OK, response.statusCode)

        val reservations = reservationRepository.findAll()
        assertEquals(2, reservations.size)

        val reservation = reservations.get(0)
        assertEquals("Test", reservation?.identity?.firstName)
        assertEquals(OffsetDateTime.parse("2024-05-25T13:15:00Z"), reservation?.movieSchedule?.schedule)
        assertEquals(1, reservation?.seat?.row)
        assertEquals(1, reservation?.seat?.number)

        val reservation2 = reservations.get(1)
        assertEquals("Test", reservation2?.identity?.firstName)
        assertEquals(OffsetDateTime.parse("2024-05-25T13:15:00Z"), reservation2?.movieSchedule?.schedule)
        assertEquals(1, reservation2?.seat?.row)
        assertEquals(2, reservation2?.seat?.number)
    }
}