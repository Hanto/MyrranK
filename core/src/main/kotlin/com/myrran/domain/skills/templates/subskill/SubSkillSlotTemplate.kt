package com.myrran.domain.skills.templates.subskill

import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlotName
import com.myrran.domain.skills.templates.Lock

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
