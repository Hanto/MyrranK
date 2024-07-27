package com.myrran.model.skills.skills.skill

import java.util.UUID

data class SkillId(
    val value: UUID
)
{
    companion object {

        @JvmStatic
        fun new(): SkillId = SkillId(UUID.randomUUID())
    }
}
