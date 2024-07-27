package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.subskill.SubSkillSlot
import com.myrran.model.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlotName
import com.myrran.model.skills.templates.Lock

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
