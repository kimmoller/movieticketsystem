package km.self.movieticketsystem.controller

import km.self.movieticketsystem.dto.IdentityCreationDto
import km.self.movieticketsystem.entity.Identity
import km.self.movieticketsystem.repository.IdentityRepository
import km.self.movieticketsystem.service.IdentityService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

class IdentityControllerTest {
    @Mock
    lateinit var identityRepository: IdentityRepository

    lateinit var identityController: IdentityController

    @BeforeEach
    fun setUp() {
        identityRepository = Mockito.mock(IdentityRepository::class.java)
        val identityService = IdentityService(identityRepository)
        identityController = IdentityController(identityService)
    }

    @Test
    fun testCreateIdentity() {
        val identityEntity = Identity(
            1,
            "username",
            "password",
            "email@test.com",
            OffsetDateTime.parse("1992-10-13T00:00:00Z"),
            "Test",
            "User",
        )
        Mockito.`when`(identityRepository.save(Mockito.any())).thenReturn(identityEntity)
        val identityCreationDto = IdentityCreationDto(
            "username",
            "password",
            "email@test.com",
            "Test",
            "User",
            OffsetDateTime.parse("1992-10-13T00:00:00Z")
        )
        val response = identityController.createIdentity(identityCreationDto)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val identity = response.body
        assertEquals("username", identity?.username)
        assertEquals("email@test.com", identity?.email)
        assertEquals("Test", identity?.firstName)
        assertEquals("User", identity?.lastName)
        assertEquals(OffsetDateTime.parse("1992-10-13T00:00:00Z"), identity?.dateOfBirth)
    }
}