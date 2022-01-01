package me.kodac.prototype.api

import me.kodac.prototype.repository.SampleMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleRestApi(
    private val sampleMapper: SampleMapper) {

    @GetMapping("/get-sample/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<Any> {

        return ResponseEntity.ok(sampleMapper.findById(id))
    }
}