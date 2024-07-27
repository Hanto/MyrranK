package com.myrran.model.skills.skills.subskill

import java.util.UUID

data class SubSkillId(
    val id: UUID
)
{
    companion object {

        @JvmStatic
        fun new(): SubSkillId = SubSkillId(UUID.randomUUID())
    }
}
