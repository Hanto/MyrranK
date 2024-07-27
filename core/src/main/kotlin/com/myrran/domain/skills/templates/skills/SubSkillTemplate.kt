package com.myrran.domain.skills.templates.skills

import com.myrran.domain.skills.skills.buffskill.BuffSkillSlots
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillId
import com.myrran.domain.skills.skills.subskill.SubSkillName
import com.myrran.domain.skills.stat.Stats
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.spells.subspell.SubSkillType

data class SubSkillTemplate(

    val id: SubSkillTemplateId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Collection<StatTemplate>,
    val slots: Collection<BuffSkillSlotTemplate>,
    val keys: Collection<LockTypes>

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
