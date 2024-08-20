package com.myrran.domain.entities.mob.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.collisionable.Collisioner
import com.myrran.domain.entities.common.collisionable.CollisionerComponent
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.corporeal.Movable
import com.myrran.domain.entities.common.corporeal.Spatial
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.misc.constants.SpellConstants.Companion.EXPIRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.IMPACT_SLOT
import com.myrran.domain.misc.constants.SpellConstants.Companion.PENETRATION
import com.myrran.domain.misc.constants.SpellConstants.Companion.SPEED
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.misc.metrics.Second
import com.myrran.domain.skills.created.form.CollisionType
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.eventbus.EventDispatcher
import ktx.math.minus

class SpellBolt(

    override val id: EntityId,
    override val steerable: SteerableComponent,
    private val eventDispatcher: EventDispatcher,

    private val consumable: ConsumableComponent,
    private val collisioner: CollisionerComponent,
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

        if (consumable.updateDuration(deltaTime).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))

        if (penetration <= 0 || collisioner.hasCollidedAWall())
            consumable.willExpireIn(Second(0))

        if (penetration > 0 && collisioner.hasCollisions()) {

            // impact slot:
            skill.getFormSkill(IMPACT_SLOT)?.also { createForm(it) }

            penetration--
            collisioner.removeCollisions()
        }
    }

    override fun dispose() =

        steerable.dispose()

    // FORM CREATION:
    //--------------------------------------------------------------------------------------------------------

    private fun createForm(skillForm: FormSkill) {

        when (skillForm.collisionType) {

            CollisionType.ON_EVERY_COLLISION_POINT -> createFormForEveryCollison(skillForm)
            CollisionType.ON_SINGLE_COLLISION_POINT -> createFormAtTheCenter(skillForm)
        }
    }

    private fun createFormForEveryCollison(skillForm: FormSkill) {

        collisioner.retrieveCollisions().forEach {

            eventDispatcher.sendEvent(FormSpellCastedEvent(
                formSkill = skillForm,
                origin = PositionMeters(it.pointOfCollision.x, it.pointOfCollision.y),
                direction = steerable.linearVelocity.cpy().nor() ))
        }
    }

    private fun createFormAtTheCenter(skillForm: FormSkill) {

        eventDispatcher.sendEvent(FormSpellCastedEvent(
            formSkill = skillForm,
            origin = PositionMeters(position.x, position.y),
            direction = steerable.linearVelocity.cpy().nor() ))
    }
}
