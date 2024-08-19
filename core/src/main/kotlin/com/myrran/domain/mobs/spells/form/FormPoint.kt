package com.myrran.domain.mobs.spells.form

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.colisionable.Collisioner
import com.myrran.domain.mobs.common.colisionable.CollisionerComponent
import com.myrran.domain.mobs.common.consumable.Consumable
import com.myrran.domain.mobs.common.consumable.ConsumableComponent
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.infraestructure.eventbus.EventDispatcher

class FormPoint(

    override val id: MobId,
    override val steerable: SteerableByBox2DComponent,
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

        steerable.position = origin.toBox2dUnits()
        steerable.saveLastPosition()
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float) {

        if (consumable.updateDuration(deltaTime).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))

        collisioner.retrieveCollisions().forEach { println(it) }
        collisioner.removeCollisions()
    }

    override fun dispose() =

        steerable.dispose()
}
