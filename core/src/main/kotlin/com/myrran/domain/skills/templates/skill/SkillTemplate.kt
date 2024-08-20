package com.myrran.domain.skills.templates.skill

import com.myrran.domain.entities.mob.spells.spell.SkillType
import com.myrran.domain.skills.created.form.FormSkillSlots
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.templates.form.FormSlotTemplate
import com.myrran.domain.skills.templates.stat.StatTemplate

data class SkillTemplate(

    val id: SkillTemplateId,
    val type: SkillType,
    val name: SkillName,
    val stats: Collection<StatTemplate>,
    val slots: Collection<FormSlotTemplate>
)
{
    fun toSkill(): Skill =

        Skill(
            id = SkillId.new(),
            templateId = id,
            type = type,
            name = name,
            customName = name,
            stats = Stats ( stats.map { it.toStat() }.associateBy { it.id } ),
            slots = FormSkillSlots( slots.map { it.toFormSkillSlot() }.associateBy { it.id } )
        )
}
