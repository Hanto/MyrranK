package com.myrran.domain.skills.templates.form

import com.myrran.domain.skills.created.effect.EffectSkillSlots
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillId
import com.myrran.domain.skills.created.form.FormSkillName
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.effect.EffectSlotTemplate
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.spells.form.FormSkillType

data class FormTemplate(

    val id: FormTemplateId,
    val type: FormSkillType,
    val name: FormSkillName,
    val stats: Collection<StatTemplate>,
    val slots: Collection<EffectSlotTemplate>,
    val keys: Collection<LockType>

)
{
    fun toFormSkill(): FormSkill =

        FormSkill(
            id = FormSkillId.new(),
            templateId = id,
            type = type,
            name = name,
            stats = Stats( stats.map { it.toStat() }.associateBy { it.id } ),
            slots = EffectSkillSlots( slots.map { it.toEffectSkillSlot() }.associateBy { it.id } ),
            keys =  keys
        )
}
