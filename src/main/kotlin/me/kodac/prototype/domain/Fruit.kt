package me.kodac.prototype.domain

class Fruit() {

    var id: String = ""

    var name: String = ""

    var kind: String = ""

    override fun toString(): String {
        return "Fruit(id='$id', name='$name', kind='$kind')"
    }

}