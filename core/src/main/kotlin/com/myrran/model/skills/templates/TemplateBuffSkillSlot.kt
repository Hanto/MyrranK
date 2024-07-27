package com.myrran.model.skills.templates

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlot
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotName

class TemplateBuffSkillSlot(

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
