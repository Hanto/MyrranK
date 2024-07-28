package com.myrran.utils

import com.badlogic.gdx.Gdx
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class DeSerializer {

    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())

    fun serialize(any: Any): String =

        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(any)

    fun <T: Any> deserialize(json: String, classz: Class<T>): T =

        objectMapper.readValue(json, classz)

    fun <T: Any> deserializeFile(path: String, classz: Class<T>): T {

        val json = Gdx.files.internal(path).readString()
        return deserialize(json, classz)
    }
}
