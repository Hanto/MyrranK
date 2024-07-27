package com.myrran.model.skills.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.myrran.model.skills.skills.skill.SkillId
import org.junit.jupiter.api.Test

class SkillBookTest
{

    @Test
    fun pim()
    {
        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())

        val skillId = SkillId.new()
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(skillId)

        println(json)

        val jsonObject = objectMapper.readValue(json, SkillId::class.java)
    }
}
