package com.myrran.domain.skills.templates.subskill

import com.myrran.domain.skills.created.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.created.subskill.SubSkillSlot
import com.myrran.domain.skills.created.subskill.SubSkillSlotId
import com.myrran.domain.skills.created.subskill.SubSkillSlotName
import com.myrran.domain.skills.lock.Lock

data class SubSkillSlotTemplate(

    val id: SubSkillSlotId,
    val name: SubSkillSlotName,
    val lock: Lock
)
{
    fun toSubSkillSlot(): SubSkillSlot =

        SubSkillSlot(
            id = id,
            name = name,
            lock = lock,
            content = NoSubSkill
        )
}
