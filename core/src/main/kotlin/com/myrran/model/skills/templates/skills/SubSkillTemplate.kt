package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlots
import com.myrran.model.skills.skills.subskill.SubSkill
import com.myrran.model.skills.skills.subskill.SubSkillId
import com.myrran.model.skills.skills.subskill.SubSkillName
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.stat.StatTemplate
import com.myrran.model.spells.subspells.SubSkillType

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
            stats = Stats( stats.map { it.toStat() } ),
            slots = BuffSkillSlots( slots.map { it.toBuffSkillSlot() } ),
            keys =  keys
        )
}
