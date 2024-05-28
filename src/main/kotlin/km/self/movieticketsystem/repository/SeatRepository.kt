package km.self.movieticketsystem.repository

import km.self.movieticketsystem.entity.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository: JpaRepository<Seat, Long?>{
}