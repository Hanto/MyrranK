package com.myrran.domain.skills.created.effect

import java.util.UUID

data class EffectSkillId(
    val value: UUID
)
{
    companion object {

        @JvmStatic
        fun new(): EffectSkillId = EffectSkillId(UUID.randomUUID())
    }
}
