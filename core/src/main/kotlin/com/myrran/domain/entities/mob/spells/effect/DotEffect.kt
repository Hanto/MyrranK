package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.misc.metrics.time.Tick
import com.myrran.domain.skills.created.effect.EffectSkill

class DotEffect(

    private val effectSkill: EffectSkill,

    private val consumable: ConsumableComponent

): Effect, Consumable by consumable
{
    init {

        val duration = effectSkill.getStat(EXPIRATION)!!.totalBonus().value.let { Tick(it) }
        consumable.willExpireIn(duration.toSeconds())
    }

    override fun tickEffect(entity: Entity, deltaTime: Float) {

        val oldTickNumber = consumable.currentDuration().toTicks().toTickNumber()

        consumable.updateDuration(Second.fromBox2DUnits(deltaTime))

        val newTickNumer = consumable.currentDuration().toTicks().toTickNumber()
    }
}

