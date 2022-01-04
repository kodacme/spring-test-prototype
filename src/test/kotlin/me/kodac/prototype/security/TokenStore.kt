package me.kodac.prototype.security

class TokenStore {

    companion object {
        private val tl = object : ThreadLocal<Context>() {
            override fun initialValue(): Context {
                return Context()
            }
        }

        fun setToken(token: String, username: String) {
            val context = tl.get()
            context.token = token
            context.username = username
        }

        fun getContext(): Context = tl.get()
    }
}