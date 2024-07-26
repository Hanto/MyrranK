package com.myrran.model.skills.skills.bdebuff

import java.util.UUID

data class BuffSkillId(
    val value: UUID
)
{
    companion object {

        @JvmStatic
        fun new(): BuffSkillId = BuffSkillId(UUID.randomUUID())
    }
}
