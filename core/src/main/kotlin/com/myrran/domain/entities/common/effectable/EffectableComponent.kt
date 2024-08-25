package com.myrran.domain.entities.common.effectable

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.mob.spells.effect.Effect
import com.myrran.domain.misc.metrics.time.Second

class EffectableComponent: Effectable {

    private var effects: MutableList<Effect> = mutableListOf()

    override fun addEffect(effect: Effect) {

        val existingEffect = effects.firstOrNull {

            it.caster.id == effect.caster.id &&
            it.effectType == effect.effectType
        }

        if (existingEffect == null) {

            effects.add(effect)
        }
        else {

            existingEffect.resetDuration()
            existingEffect.increaseStack()
        }
    }

    override fun updateEffects(entity: Entity, deltaTime: Second) {

        effects.forEach { updateEffect(it, entity, deltaTime)}

        effects.removeIf { it.hasExpired() }
    }

    private fun updateEffect(effect: Effect, entity: Entity, deltaTime: Second) {

        if (effect.hasStarted())
            effect.effectStarted(entity)

        val oldTick = effect.currentDuration().toTicks().toTickNumber()

        effect.update(deltaTime)

        val newTick = effect.currentDuration().toTicks().toTickNumber()

        repeat( newTick - oldTick ) { effect.effectTicked(entity) }

        if (effect.hasExpired())
            effect.effectEnded(entity)
    }
}
