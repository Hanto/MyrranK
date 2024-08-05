package com.myrran.domain.skills.templates.subskill

import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlots
import com.myrran.domain.skills.custom.stat.Stats
import com.myrran.domain.skills.custom.subskill.SubSkillId
import com.myrran.domain.skills.custom.subskill.SubSkillName
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.stat.StatTemplate
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
