package com.myrran.domain.skills.templates

import com.myrran.domain.skills.created.BuffSkill
import com.myrran.domain.skills.created.buff.BuffSkillId
import com.myrran.domain.skills.created.buff.BuffSkillName
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
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
