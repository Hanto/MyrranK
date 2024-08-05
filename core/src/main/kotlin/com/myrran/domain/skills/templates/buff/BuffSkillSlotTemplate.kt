package com.myrran.domain.skills.templates.buff

import com.myrran.domain.skills.custom.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.buff.BuffSkillSlotName
import com.myrran.domain.skills.templates.Lock

data class BuffSkillSlotTemplate(

    val id: BuffSkillSlotId,
    val name: BuffSkillSlotName,
    val lock: Lock
)
{
    fun toBuffSkillSlot(): BuffSkillSlot =

        BuffSkillSlot(
            id = id,
            name = name,
            lock = lock,
            content = NoBuffSkill
        )
}
