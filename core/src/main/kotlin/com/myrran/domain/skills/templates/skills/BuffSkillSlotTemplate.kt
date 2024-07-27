package com.myrran.domain.skills.templates.skills

import com.myrran.domain.skills.skills.buffskill.BuffSkillSlot
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotId
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotName
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
