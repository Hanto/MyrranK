package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.skill.Skill
import com.myrran.model.skills.skills.skill.SkillId
import com.myrran.model.skills.skills.skill.SkillName
import com.myrran.model.skills.skills.subskill.SubSkillSlots
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.templates.stat.StatTemplate
import com.myrran.model.spells.spell.SkillType

data class SkillTemplate(

    val id: SkillTemplateId,
    val type: SkillType,
    val name: SkillName,
    val stats: Collection<StatTemplate>,
    val slots: Collection<SubSkillSlotTemplate>
)
{
    fun toSkill(): Skill =

        Skill(
            id = SkillId.new(),
            templateId = id,
            type = type,
            name = name,
            stats = Stats ( stats.map { it.toStat() }.associateBy { it.id } ),
            slots = SubSkillSlots( slots.map { it.toSubSkillSlot() }.associateBy { it.id } )
        )
}
