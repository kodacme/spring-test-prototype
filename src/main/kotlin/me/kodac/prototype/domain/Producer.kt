package me.kodac.prototype.domain

class Producer {

    var id: String = ""

    var address: String? = null

    var lastname: String = ""

    var firstname: String = ""

    override fun toString(): String {
        return "Producer(id='$id', address='$address', lastname='$lastname', firstname='$firstname')"
    }

}