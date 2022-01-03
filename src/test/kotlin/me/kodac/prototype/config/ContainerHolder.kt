package me.kodac.prototype.config

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

class ContainerHolder private constructor() : PostgreSQLContainer<ContainerHolder>(IMAGE) {

    companion object {
        private val IMAGE: DockerImageName = DockerImageName.parse("postgres:14")
        private var container: ContainerHolder? = null

        fun getInstance(): ContainerHolder {
            return container ?: ContainerHolder()
        }
    }

    init {
        start()
    }

    override fun stop() {
        // do nothing
    }
}