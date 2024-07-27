package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.skill.Skill
import com.myrran.model.skills.skills.skill.SkillId
import com.myrran.model.skills.skills.skill.SkillName
import com.myrran.model.skills.skills.subskill.SubSkillSlots
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.templates.stat.TemplateStat
import com.myrran.model.spells.spell.SkillType

data class SkillTemplate(

    val type: SkillType,
    val name: SkillName,
    val stats: Collection<TemplateStat>,
    val slots: Collection<SubSkillSlotTemplate>
)
{
    fun toSkill(): Skill =

        Skill(
            id = SkillId.new(),
            type = type,
            name = name,
            stats = Stats ( stats.map { it.toStat() } ),
            slots = SubSkillSlots( slots.map { it.toSubSkillSlot() })
        )
}
