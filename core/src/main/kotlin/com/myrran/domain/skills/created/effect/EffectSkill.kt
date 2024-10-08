package com.myrran.domain.skills.created.effect

import com.myrran.domain.entities.mob.spells.effect.EffectType
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.created.stat.StatsI
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.effect.EffectTemplateId

data class EffectSkill(

    val id: EffectSkillId,
    val templateId: EffectTemplateId,
    val type: EffectType,
    val name: EffectSkillName,
    val stats: Stats,
    val keys: Collection<LockType>

): EffectSkillSlotContent, StatsI by stats

sealed interface EffectSkillSlotContent {

    data object NoEffectSkill: EffectSkillSlotContent
}
