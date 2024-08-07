package com.myrran.domain.misc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class DeSerializer {

    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())

    fun serialize(any: Any): String =

        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(any)

    fun <T: Any> deserialize(json: String, classz: Class<T>): T =

        objectMapper.readValue(json, classz)
}
