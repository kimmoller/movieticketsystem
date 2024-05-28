package km.self.movieticketsystem.dto

import km.self.movieticketsystem.entity.MovieSchedule

data class ReservationDto(val identityId: Long, val movieScheduleId: Long, val seatIds: List<Long>)
