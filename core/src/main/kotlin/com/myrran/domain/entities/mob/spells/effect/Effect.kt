package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.statuses.Status
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.skills.created.effect.EffectSkillId
import com.myrran.domain.skills.created.effect.EffectSkillName

interface Effect: Identifiable<EntityId>, Consumable, Stackable {

    val caster: Entity
    val effectType: EffectType
    val effectSkillId: EffectSkillId
    val allowToStack: Boolean
    val statusEffects: List<Status>
    val damages: MutableList<Damage>
    fun effectName(): EffectSkillName
    fun onEffectStarted(target: Entity)
    fun ofEffectTicked(target: Entity)
    fun onEffectEnded(target: Entity)
}
