package me.kodac.prototype

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PrototypeApplication

fun main(args: Array<String>) {
	runApplication<PrototypeApplication>(*args)
}
