package com.myrran.domain.skills.created.subskill

import java.util.UUID

data class SubSkillId(
    val value: UUID
)
{
    companion object {

        @JvmStatic
        fun new(): SubSkillId = SubSkillId(UUID.randomUUID())
    }
}
