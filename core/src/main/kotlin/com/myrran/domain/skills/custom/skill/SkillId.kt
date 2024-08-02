package com.myrran.domain.skills.custom.skill

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
