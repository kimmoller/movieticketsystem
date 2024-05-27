package km.self.movieticketsystem.dto

import km.self.movieticketsystem.entity.Hall
import km.self.movieticketsystem.entity.Theater

data class HallDto(val theater: Theater, val number: Int)
