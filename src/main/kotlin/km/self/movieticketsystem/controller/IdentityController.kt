package km.self.movieticketsystem.controller

import km.self.movieticketsystem.dto.IdentityCreationDto
import km.self.movieticketsystem.dto.IdentityDto
import km.self.movieticketsystem.service.IdentityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("identity")
class IdentityController(private val identityService: IdentityService) {
    @PostMapping("/")
    fun createIdentity(@RequestBody identityCreationDto: IdentityCreationDto): ResponseEntity<IdentityDto> {
        val identityDto = identityService.createIdentity(identityCreationDto)
        return ResponseEntity(identityDto, HttpStatus.CREATED)
    }
}