package com.myrran.domain.skills.templates.effect

import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.effect.EffectSkillSlotContent.NoEffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.effect.EffectSkillSlotName
import com.myrran.domain.skills.lock.Lock

data class EffectSlotTemplate(

    val id: EffectSkillSlotId,
    val name: EffectSkillSlotName,
    val lock: Lock
)
{
    fun toEffectSkillSlot(): EffectSkillSlot =

        EffectSkillSlot(
            id = id,
            name = name,
            lock = lock,
            content = NoEffectSkill
        )
}
