package km.self.movieticketsystem.service

import km.self.movieticketsystem.dto.IdentityCreationDto
import km.self.movieticketsystem.dto.IdentityDto
import km.self.movieticketsystem.entity.Identity
import km.self.movieticketsystem.repository.IdentityRepository
import org.springframework.stereotype.Service

@Service
class IdentityService(private val identityRepository: IdentityRepository) {
    val secretKey = AESEncryptor.generateAESKey()
    fun createIdentity(identityCreationDto: IdentityCreationDto): IdentityDto {
        val hashedPassword = AESEncryptor.aesEncrypt(identityCreationDto.password.toByteArray(), secretKey)
        val identity = Identity(
            null,
            identityCreationDto.username,
            hashedPassword.toString(),
            identityCreationDto.email,
            identityCreationDto.dateOfBirth,
            identityCreationDto.firstName,
            identityCreationDto.lastName)

        val identityEntity = identityRepository.save(identity)
        return IdentityDto(
            identityEntity.username,
            identityEntity.email,
            identityEntity.dateOfBirth,
            identityEntity.firstName,
            identityEntity.lastName
        )
    }
}