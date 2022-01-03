package me.kodac.prototype.config

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

object ContainerHolder {

    private val IMAGE: DockerImageName = DockerImageName.parse("postgres:14")

    @Container
    private val POSTGRES_CONTAINER: PostgreSQLContainer<*> = PostgreSQLContainer<PostgreSQLContainer<*>>(IMAGE)
        .withDatabaseName("dev")
        .withUsername("postgres")
        .withPassword("postgres")
        .withExposedPorts(5432)
        .withEnv("POSTGRES_INITDB_ARGS", "--encoding=UTF-8 --locale=C")

    init {
        POSTGRES_CONTAINER.start()
    }

    fun getContainer(): PostgreSQLContainer<*> {
        return POSTGRES_CONTAINER
    }
}