package me.kodac.prototype.api

import com.github.springtestdbunit.annotation.DatabaseSetup
import me.kodac.prototype.config.DatabaseConfiguration
import me.kodac.prototype.domain.Fruit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus

@Import(DatabaseConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleApiTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    @DatabaseSetup("/dbunit/sample-api/expected01.xml")
    fun test01() {

        val res = restTemplate.getForEntity("/get-sample/F001", Fruit::class.java)

        assertEquals(HttpStatus.OK, res.statusCode)
    }
}