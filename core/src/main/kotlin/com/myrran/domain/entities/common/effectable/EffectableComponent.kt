package com.myrran.domain.entities.common.effectable

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.movementlimiter.MovementLimiter
import com.myrran.domain.entities.common.statuses.Statuses
import com.myrran.domain.entities.common.statuses.StatusesComponent
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.mob.spells.effect.Effect
import com.myrran.domain.events.EffectAddedEvent
import com.myrran.domain.events.EffectRemovedEvent
import com.myrran.domain.events.EffectTickedEvent
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.infraestructure.eventbus.EventDispatcher

class EffectableComponent(

    private val statuses: StatusesComponent,
    private val eventDispatcher: EventDispatcher,

): Effectable, Statuses by statuses {

    override fun retrieveEffects(): List<Effect> =

        statuses.findAll()

    override fun addEffect(effect: Effect) {

        val existingEffect = statuses.findByCasterAndType(effect.caster.id, effect.effectType)

        if (existingEffect == null) {

            statuses.addEffect(effect)
        }
        else if (existingEffect.effectSkillId == effect.effectSkillId) {

            existingEffect.resetDuration()
            existingEffect.increaseStack()
        }
        else if (effect.allowToStack) {

            statuses.addEffect(effect)
        }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    override fun updateEffects(effectable: Entity, deltaTime: Second) {

        statuses.findAll().forEach { effect ->

            if (effect.hasStarted())
                effectStarted(effectable, effect)

            updateEffect(effectable, effect, deltaTime)
        }

        removeExpired(effectable)

        applyAll(effectable)
    }

    // STEPS:
    //--------------------------------------------------------------------------------------------------------

    private fun effectStarted(entity: Entity, effect: Effect) {

        effect.onEffectStarted(entity)
        eventDispatcher.sendEvent(EffectAddedEvent(entity.id, effect.id))
    }

    private fun updateEffect(entity: Entity, effect: Effect, deltaTime: Second) {

        val oldTick = effect.currentDuration().toTicks().toTickNumber()

        effect.updateDuration(deltaTime)

        val newTick = effect.currentDuration().toTicks().toTickNumber()

        repeat( newTick - oldTick ) {

            effect.ofEffectTicked(entity)
            eventDispatcher.sendEvent(EffectTickedEvent(entity.id, effect.id))
        }
    }

    private fun removeExpired(entity: Entity) {

        val toBeRemoved = statuses.findExpired()

        if  (toBeRemoved.isNotEmpty()) {

            statuses.removeExpired()

            toBeRemoved.forEach { effect ->

                effect.onEffectEnded(entity)
                eventDispatcher.sendEvent(EffectRemovedEvent(entity.id, effect.id))
            }
        }
    }

    // APPLY ALL:
    //--------------------------------------------------------------------------------------------------------

    private fun applyAll(entity: Entity) {

        statuses.recalc()

        if (entity is Vulnerable) {

            statuses.damage().forEach { entity.receiveDamage(it) }
        }

        if (entity is MovementLimiter) {

            entity.slowModifier = statuses.slowMagnitude()
        }
    }
}
