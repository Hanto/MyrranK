package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.skills.created.effect.EffectSkillName

interface Effect: Identifiable<EntityId>, Consumable, Stackable {

    val caster: Entity
    val effectType: EffectType
    fun effectName(): EffectSkillName
    fun effectStarted(entity: Entity)
    fun effectTicked(entity: Entity)
    fun effectEnded(entity: Entity)
}
