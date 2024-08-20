package com.myrran.domain.mobs.spells.form

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.misc.metrics.Second
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.collisionable.Collisioner
import com.myrran.domain.mobs.common.collisionable.CollisionerComponent
import com.myrran.domain.mobs.common.consumable.Consumable
import com.myrran.domain.mobs.common.consumable.ConsumableComponent
import com.myrran.domain.mobs.common.corporeal.Movable
import com.myrran.domain.mobs.common.corporeal.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableComponent
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.infraestructure.eventbus.EventDispatcher

class FormPoint(

    override val id: MobId,
    override val steerable: SteerableComponent,
    private val eventDispatcher: EventDispatcher,

    private val consumable: ConsumableComponent,
    private val collisioner: CollisionerComponent,
    val formSkill: FormSkill,
    origin: PositionMeters,
    direction: Vector2,

    ): Mob, Identifiable<MobId>, Steerable by steerable, Spatial, Movable, Disposable,
    Form, Consumable by consumable, Collisioner by collisioner
{
    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        // initial position:
        steerable.position = origin.toBox2dUnits()
        steerable.saveLastPosition()

        // expiration time:
        val expirationTime = formSkill.getStat(EXPIRATION)!!.totalBonus().value.let { Second(it) }
        consumable.willExpireIn(expirationTime)
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float) {

        if (consumable.updateDuration(deltaTime).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))

        collisioner.retrieveCollisions().size.also { println(it) }
        collisioner.removeCollisions()
    }

    override fun dispose() =

        steerable.dispose()
}
