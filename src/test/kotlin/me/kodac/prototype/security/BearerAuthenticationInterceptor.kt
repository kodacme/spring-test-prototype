package me.kodac.prototype.security

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class BearerAuthenticationInterceptor : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val context = TokenStore.getContext()
        val jwt = context.token
        if (jwt.isNotEmpty()) {
            request.headers.setBearerAuth(jwt)
        }
        return execution.execute(request, body)
    }
}