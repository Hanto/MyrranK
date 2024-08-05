package com.myrran.domain.skills.custom.skill

import java.util.UUID

data class SkillId(
    val value: UUID = UUID.randomUUID()
)
{
    companion object {

        @JvmStatic
        fun new(): SkillId = SkillId(UUID.randomUUID())
        fun from(string: String) = SkillId(UUID.fromString(string))
    }
}
