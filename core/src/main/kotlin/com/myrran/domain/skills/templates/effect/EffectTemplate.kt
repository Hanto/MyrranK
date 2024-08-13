package com.myrran.domain.skills.templates.effect

import com.myrran.domain.mobs.spells.effect.EffectType
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillId
import com.myrran.domain.skills.created.effect.EffectSkillName
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.stat.StatTemplate

data class EffectTemplate(

    val id: EffectTemplateId,
    val type: EffectType,
    val name: EffectSkillName,
    val stats: Collection<StatTemplate>,
    val keys: Collection<LockType>
)
{
    fun toEffectSkill(): EffectSkill =

        EffectSkill(
            id = EffectSkillId.new(),
            templateId = id,
            type = type,
            name = name,
            stats = Stats( stats.map { it.toStat() }.associateBy { it.id } ),
            keys = keys
        )
}
