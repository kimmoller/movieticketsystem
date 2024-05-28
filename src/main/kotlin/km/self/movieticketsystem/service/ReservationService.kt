package km.self.movieticketsystem.service

import km.self.movieticketsystem.dto.ReservationDto
import km.self.movieticketsystem.entity.Reservation
import km.self.movieticketsystem.repository.IdentityRepository
import km.self.movieticketsystem.repository.MovieScheduleRepository
import km.self.movieticketsystem.repository.ReservationRepository
import km.self.movieticketsystem.repository.SeatRepository
import org.springframework.stereotype.Service

@Service
class ReservationService(
    val identityRepository: IdentityRepository,
    val movieScheduleRepository: MovieScheduleRepository,
    val seatRepository: SeatRepository,
    val reservationRepository: ReservationRepository
) {
    fun reserveTickets(reservationDto: ReservationDto) {
        val identity = identityRepository.findById(reservationDto.identityId).orElseThrow()

        val movieSchedule = movieScheduleRepository.findById(reservationDto.movieScheduleId).orElseThrow()

        val reservations = ArrayList<Reservation>()
        reservationDto.seatIds.forEach {
            val seat = seatRepository.findById(it).orElseThrow()
            val reservation = Reservation(null, identity, movieSchedule, seat)
            reservations.add(reservation)
        }

        reservationRepository.saveAll(reservations)
    }
}