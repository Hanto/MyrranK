package com.myrran.domain.skills.templates.buff

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillId
import com.myrran.domain.skills.custom.buff.BuffSkillName
import com.myrran.domain.skills.custom.stat.Stats
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.domain.spells.buff.BuffType

data class BuffSkillTemplate(

    val id: BuffSkillTemplateId,
    val type: BuffType,
    val name: BuffSkillName,
    val stats: Collection<StatTemplate>,
    val keys: Collection<LockType>
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
