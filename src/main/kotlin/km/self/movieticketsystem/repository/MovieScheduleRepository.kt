package km.self.movieticketsystem.repository

import km.self.movieticketsystem.entity.MovieSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieScheduleRepository : JpaRepository<MovieSchedule, Long?>