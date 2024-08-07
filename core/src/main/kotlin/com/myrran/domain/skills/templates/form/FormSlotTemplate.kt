package com.myrran.domain.skills.templates.form

import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotContent.NoFormSkill
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotName
import com.myrran.domain.skills.lock.Lock

data class FormSlotTemplate(

    val id: FormSkillSlotId,
    val name: FormSkillSlotName,
    val lock: Lock
)
{
    fun toFormSkillSlot(): FormSkillSlot =

        FormSkillSlot(
            id = id,
            name = name,
            lock = lock,
            content = NoFormSkill
        )
}
