package com.myrran.domain.entities.common.effectable

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.mob.spells.effect.Effect
import com.myrran.domain.events.EffectAddedEvent
import com.myrran.domain.events.EffectRemovedEvent
import com.myrran.domain.events.EffectTickedEvent
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.infraestructure.eventbus.EventDispatcher

class EffectableComponent(

    private val eventDispatcher: EventDispatcher

): Effectable {

    private var effects: MutableList<Effect> = mutableListOf()

    override fun retrieveEffects(): List<Effect> =

        effects

    override fun addEffect(effect: Effect) {

        val existingEffect = effects.firstOrNull {

            it.caster.id == effect.caster.id &&
            it.effectType == effect.effectType
        }

        if (existingEffect == null) {

            effects.add(effect)
        }
        else {

            existingEffect.resetDurationTo(Second(0.0f))
            existingEffect.increaseStack()
        }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    override fun updateEffects(effectable: Entity, deltaTime: Second) {

        effects.forEach { effect ->

            if (effect.hasStarted())
                effectStarted(effectable, effect)

            updateEffect(effectable, effect, deltaTime)
        }

        removeExpired(effectable)
    }

    // STEPS:
    //--------------------------------------------------------------------------------------------------------

    private fun effectStarted(entity: Entity, effect: Effect) {

        effect.effectStarted(entity)
        eventDispatcher.sendEvent(EffectAddedEvent(entity.id, effect.id))
    }

    private fun updateEffect(entity: Entity, effect: Effect, deltaTime: Second) {

        val oldTick = effect.currentDuration().toTicks().toTickNumber()

        effect.updateDuration(deltaTime)

        val newTick = effect.currentDuration().toTicks().toTickNumber()

        repeat( newTick - oldTick ) {

            effect.effectTicked(entity)
            eventDispatcher.sendEvent(EffectTickedEvent(entity.id, effect.id))
        }
    }

    private fun removeExpired(entity: Entity) {

        val toBeRemoved = effects.filter { it.hasExpired() }

        if  (toBeRemoved.isNotEmpty()) {

            effects.removeIf { it.hasExpired() }

            toBeRemoved.forEach { effect ->

                effect.effectEnded(entity)
                eventDispatcher.sendEvent(EffectRemovedEvent(entity.id, effect.id))
            }
        }
    }
}
