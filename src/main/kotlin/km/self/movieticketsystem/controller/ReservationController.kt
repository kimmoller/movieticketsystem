package km.self.movieticketsystem.controller

import km.self.movieticketsystem.dto.ReservationDto
import km.self.movieticketsystem.service.ReservationService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("reserve")
class ReservationController(val reservationService: ReservationService) {

    @PostMapping("/movie")
    fun reserveTickets(@RequestBody reservationDto: ReservationDto): ResponseEntity<Nothing> {
        try {
            reservationService.reserveTickets(reservationDto)
            return ResponseEntity(null, HttpStatus.OK)
        } catch (error: NoSuchElementException) {
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        } catch (error: DataIntegrityViolationException) {
            return ResponseEntity(null, HttpStatus.CONFLICT)
        } catch (error: Error) {
            return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}