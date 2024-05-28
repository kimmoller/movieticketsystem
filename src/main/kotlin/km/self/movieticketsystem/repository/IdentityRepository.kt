package km.self.movieticketsystem.repository

import km.self.movieticketsystem.entity.Identity
import org.springframework.data.jpa.repository.JpaRepository

interface IdentityRepository: JpaRepository<Identity, Long?> {
}