package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.skills.created.effect.EffectSkill

class DamageEffect(

    private val effectSkill: EffectSkill,

    private val consumable: ConsumableComponent = ConsumableComponent()

): Effect, Consumable by consumable
{

    override fun tickEffect(entity: Entity, deltaTime: Float) {

        val oldTickNumber = consumable.currentDuration().toTicks().toTickNumber()

        consumable.updateDuration(Second.fromBox2DUnits(deltaTime))

        val newTickNumer = consumable.currentDuration().toTicks().toTickNumber()
    }
}
