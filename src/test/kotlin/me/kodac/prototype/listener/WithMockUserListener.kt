package me.kodac.prototype.listener

import me.kodac.prototype.security.TestJwtGenerator
import me.kodac.prototype.security.TokenStore
import org.junit.platform.commons.util.AnnotationUtils
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListener
import java.util.*

class WithMockUserListener : TestExecutionListener {

    private val jwtGenerator = TestJwtGenerator()

    override fun beforeTestMethod(testContext: TestContext) {
        val marked = getAnnotation(testContext)

        if (!marked.isEmpty) {
            val username = marked.get().username
            val jwt = jwtGenerator.generate(username)
            TokenStore.setToken(jwt, username)
        }
    }

    override fun afterTestMethod(testContext: TestContext) {
        val marked = getAnnotation(testContext)

        if (!marked.isEmpty) {
            val context = TokenStore.getContext()
            context.initialize()
        }
    }

    private fun getAnnotation(testContext: TestContext): Optional<WithMockUser> {
        return AnnotationUtils.findAnnotation(
            testContext.testMethod, WithMockUser::class.java
        )
    }
}