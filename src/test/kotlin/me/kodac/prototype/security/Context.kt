package me.kodac.prototype.security

class Context {

    var username: String = ""

    var token: String = ""

    fun initialize(): Context {
        username = ""
        token = ""
        return this
    }

}