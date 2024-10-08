package com.myrran.domain.entities.mob.spells.form

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.collisioner.CollisionerComponent
import com.myrran.domain.entities.common.collisioner.ExactLocation
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.corporeal.Movable
import com.myrran.domain.entities.common.corporeal.Spatial
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.entities.mob.spells.form.effectapplier.EffectApplierComponent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.infraestructure.eventbus.EventDispatcher

class FormPoint(

    override val id: EntityId,
    override val caster: Entity,
    override val steerable: SteerableComponent,
    private val eventDispatcher: EventDispatcher,

    private val consumable: ConsumableComponent,
    private val collisioner: CollisionerComponent,
    private val effectApplier: EffectApplierComponent,
    private val formSkill: FormSkill,
    private val location: ExactLocation,

): Mob, Steerable by steerable, Spatial, Movable, Disposable,
    Form, Consumable by consumable, Collisioner by collisioner
{
    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        // initial position:
        steerable.position = location.origin.toBox2dUnits()
        steerable.saveLastPosition()

        // expiration time:
        val expirationTime = formSkill.getStat(EXPIRATION)!!.totalBonus().value.let { Second(it) }
        consumable.willExpireIn(expirationTime)
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float) {

        if (consumable.updateDuration(Second.fromBox2DUnits(deltaTime)).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))

        if (collisioner.hasCollisions()) {

            collisioner.retrieveCollisions().forEach { applyEffects(it) }
            collisioner.removeCollisions()
        }
    }

    override fun dispose() =

        steerable.dispose()

    override fun addCollision(collisioned: Corporeal, pointOfCollision: PositionMeters) {

        collisioner.addCollision(collisioned, pointOfCollision, location.direction)
    }

    private fun applyEffects(collision: Collision) {

        formSkill.getEffectSkills().forEach { effectSkill ->

            effectApplier.applyEffects(caster, effectSkill, collision)
        }
    }
}
