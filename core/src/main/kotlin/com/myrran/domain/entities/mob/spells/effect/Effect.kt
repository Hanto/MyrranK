package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.mob.spells.effect.stackable.Stackable
import com.myrran.domain.misc.metrics.time.Second

interface Effect: Consumable, Stackable {

    val caster: Entity
    val effectType: EffectType
    fun effectStarted(entity: Entity)
    fun effectTicked(entity: Entity)
    fun effectEnded(entity: Entity)
    fun update(deltaTime: Second)

}
