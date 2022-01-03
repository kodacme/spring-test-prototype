package me.kodac.prototype.api

import me.kodac.prototype.domain.Producer
import me.kodac.prototype.repository.ProducerMapper
import me.kodac.prototype.repository.SampleMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SampleRestApi(
    private val sampleMapper: SampleMapper,
    private val producerMapper: ProducerMapper
) {

    @GetMapping("/get-sample/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<Any> {

        return ResponseEntity.ok(sampleMapper.findById(id))
    }

    @PostMapping("/post-sample")
    fun post(@RequestBody producer: Producer): ResponseEntity<Any> {

        producerMapper.save(producer)
        return ResponseEntity.ok(mapOf("res" to "ok"))
    }
}