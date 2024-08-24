package com.myrran.domain.entities.mob.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.collisioner.CollisionerComponent
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.corporeal.Movable
import com.myrran.domain.entities.common.corporeal.Spatial
import com.myrran.domain.entities.mob.spells.spell.formcreator.FormCreatorComponent
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.IMPACT_SLOT
import com.myrran.domain.misc.constants.SpellConstants.Companion.PENETRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.SPEED
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.eventbus.EventDispatcher
import ktx.math.minus

class SpellBolt(

    override val id: EntityId,
    override val steerable: SteerableComponent,
    private val eventDispatcher: EventDispatcher,

    private val consumable: ConsumableComponent,
    private val collisioner: CollisionerComponent,
    private val formCreator: FormCreatorComponent,
    val skill: Skill,
    origin: PositionMeters,
    target: PositionMeters,

): Mob, Steerable by steerable, Spatial, Movable, Disposable,
    Spell, Consumable by consumable, Collisioner by collisioner
{
    private var penetration = 1

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        // initial position:
        steerable.position = origin.toBox2dUnits()
        steerable.saveLastPosition()

        // direction:
        val direction = target.toBox2dUnits().minus(position).nor()
        val speed = skill.getStat(SPEED)!!.totalBonus()
        steerable.applyImpulse(direction, speed.value)

        // expiration time:
        val expirationTime = skill.getStat(EXPIRATION)!!.totalBonus().value.let { Second(it) }
        consumable.willExpireIn(expirationTime)

        // penetration:
        penetration = skill.getStat(PENETRATION)!!.totalBonus().value.toInt()
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float) {

        if (consumable.updateDuration(Second.fromBox2DUnits(deltaTime)).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))

        if (penetration <= 0 || collisioner.hasCollidedAWall())
            consumable.willExpireIn(Second(0))

        if (penetration > 0 && collisioner.hasCollisions()) {

            // impact slot:
            skill.getFormSkill(IMPACT_SLOT)
                ?.also { formCreator.createForm(it, this) }

            penetration--
            collisioner.removeCollisions()
        }
    }

    override fun dispose() =

        steerable.dispose()

    override fun addCollision(collisioned: Corporeal, pointOfCollision: PositionMeters)
    {
        val direction = steerable.linearVelocity.cpy()
        collisioner.addCollision(collisioned, pointOfCollision, direction)
    }
}
