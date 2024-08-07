package com.myrran.domain.skills.templates

import com.myrran.domain.skills.created.SubSkill
import com.myrran.domain.skills.created.buff.BuffSkillSlots
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.created.subskill.SubSkillId
import com.myrran.domain.skills.created.subskill.SubSkillName
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.spells.subspell.SubSkillType

data class SubSkillTemplate(

    val id: SubSkillTemplateId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Collection<StatTemplate>,
    val slots: Collection<BuffSkillSlotTemplate>,
    val keys: Collection<LockType>

)
{
    fun toSubSkill(): SubSkill =

        SubSkill(
            id = SubSkillId.new(),
            templateId = id,
            type = type,
            name = name,
            stats = Stats( stats.map { it.toStat() }.associateBy { it.id } ),
            slots = BuffSkillSlots( slots.map { it.toBuffSkillSlot() }.associateBy { it.id } ),
            keys =  keys
        )
}
