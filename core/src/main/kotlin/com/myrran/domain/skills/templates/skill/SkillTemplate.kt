package com.myrran.domain.skills.templates.skill

import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.skill.SkillName
import com.myrran.domain.skills.custom.stat.Stats
import com.myrran.domain.skills.custom.subskill.SubSkillSlots
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillSlotTemplate
import com.myrran.domain.spells.spell.SkillType

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
