package com.myrran.model.skills.templates.skills

import com.myrran.model.skills.skills.buffSkill.BuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillId
import com.myrran.model.skills.skills.buffSkill.BuffSkillName
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.stat.StatTemplate
import com.myrran.model.spells.buffs.BuffType

data class BuffSkillTemplate(

    val id: BuffSkillTemplateId,
    val type: BuffType,
    val name: BuffSkillName,
    val stats: Collection<StatTemplate>,
    val keys: Collection<LockTypes>
)
{
    fun toBuffSkill(): BuffSkill =

        BuffSkill(
            id = BuffSkillId.new(),
            templateId = id,
            type = type,
            name = name,
            stats = Stats( stats.map { it.toStat() } )
        )
}
