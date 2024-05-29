package km.self.movieticketsystem.controller

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ControllerTest {
    companion object {
        lateinit var flyway: Flyway
        val db = PostgreSQLContainer("postgres")

        @BeforeAll
        @JvmStatic
        fun startDBContainer() {
            db.start()
            flyway = Flyway
                .configure()
                .dataSource(db.jdbcUrl, db.username, db.password)
                .cleanDisabled(false)
                .load()
            flyway.migrate()
        }

        @AfterAll
        @JvmStatic
        fun stopDBContainer() {
            flyway.clean()
            flyway.migrate()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", db::getJdbcUrl)
            registry.add("spring.datasource.username", db::getUsername)
            registry.add("spring.datasource.password", db::getPassword)
        }
    }
}