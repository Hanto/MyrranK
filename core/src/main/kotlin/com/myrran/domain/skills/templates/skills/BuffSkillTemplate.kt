package com.myrran.domain.skills.templates.skills

import com.myrran.domain.skills.skills.buffskill.BuffSkill
import com.myrran.domain.skills.skills.buffskill.BuffSkillId
import com.myrran.domain.skills.skills.buffskill.BuffSkillName
import com.myrran.domain.skills.stat.Stats
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.spells.buffspell.BuffType

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
            stats = Stats( stats.map { it.toStat() }.associateBy { it.id } ),
            keys = keys
        )
}
