package km.self.movieticketsystem.controller

import km.self.movieticketsystem.dto.ReservationDto
import km.self.movieticketsystem.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController(val reservationService: ReservationService) {

    @PostMapping("/reserve/movie")
    fun reserveTickets(@RequestBody reservationDto: ReservationDto): ResponseEntity<Nothing> {
        reservationService.reserveTickets(reservationDto)
        return ResponseEntity(null, HttpStatus.OK)
    }
}