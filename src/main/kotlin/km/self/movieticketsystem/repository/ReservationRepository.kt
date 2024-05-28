package km.self.movieticketsystem.repository

import km.self.movieticketsystem.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository: JpaRepository<Reservation, Long?> {
}