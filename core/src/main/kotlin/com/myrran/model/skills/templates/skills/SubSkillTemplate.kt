package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlots
import com.myrran.model.skills.skills.subskill.SubSkill
import com.myrran.model.skills.skills.subskill.SubSkillId
import com.myrran.model.skills.skills.subskill.SubSkillName
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.stat.TemplateStat
import com.myrran.model.spells.subspells.SubSkillType

data class SubSkillTemplate(

    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Collection<TemplateStat>,
    val slots: Collection<TemplateBuffSkillSlot>,
    val keys: Collection<LockTypes>

)
{
    fun toSubSkill(): SubSkill =

        SubSkill(
            id = SubSkillId.new(),
            type = type,
            name = name,
            stats = Stats( stats.map { it.toStat() } ),
            slots = BuffSkillSlots( slots.map { it.toBuffSkillSlot() } )
        )
}
