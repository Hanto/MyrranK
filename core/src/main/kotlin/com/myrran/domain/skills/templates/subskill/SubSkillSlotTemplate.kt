package com.myrran.domain.skills.templates.subskill

import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotName
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
