package me.kodac.prototype.api

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DbUnitConfiguration
import com.github.springtestdbunit.annotation.ExpectedDatabase
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader
import me.kodac.prototype.config.DatabaseConfiguration
import me.kodac.prototype.domain.Producer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import java.nio.file.Files
import java.nio.file.Path

@Import(DatabaseConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader::class)
@TestExecutionListeners(
    DependencyInjectionTestExecutionListener::class,
    DirtiesContextTestExecutionListener::class,
    TransactionDbUnitTestExecutionListener::class
)
class SampleApiTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    @DatabaseSetup("/dbunit/sample-api/setup_01.xml")
    fun test01() {

        val res = restTemplate.getForEntity("/get-sample/F001", String::class.java)

        assertEquals(HttpStatus.OK, res.statusCode)
        JSONAssert.assertEquals(
            content("/api-result/sample-api/result_01.json"),
            res.body,
            JSONCompareMode.STRICT
        )
    }

    @Test
    @DatabaseSetup("/dbunit/sample-api/setup_01.xml")
    @ExpectedDatabase("/dbunit/sample-api/expected_01.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    fun test02() {
        val pro = Producer()
        pro.apply {
            id = "P001"
            lastname = "山田"
            firstname = "taro"
        }
        val res = restTemplate.postForEntity("/post-sample", pro, String::class.java)

        assertEquals(HttpStatus.OK, res.statusCode)
        JSONAssert.assertEquals(
            content("/api-result/sample-api/result_02.json"),
            res.body,
            JSONCompareMode.STRICT
        )
    }

    private fun content(path: String): String {
        val s = if (path.startsWith("/")) path else "/$path"
        val p = Path.of("src/test/resources$s")
        return Files.readString(p)
    }
}