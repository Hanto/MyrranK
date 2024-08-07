package com.myrran.domain.skills.templates.buff

import com.myrran.domain.skills.created.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.created.buff.BuffSkillSlot
import com.myrran.domain.skills.created.buff.BuffSkillSlotId
import com.myrran.domain.skills.created.buff.BuffSkillSlotName
import com.myrran.domain.skills.lock.Lock

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
