package com.myrran.domain.mobs.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.collisionable.Collisioner
import com.myrran.domain.mobs.common.collisionable.CollisionerComponent
import com.myrran.domain.mobs.common.consumable.Consumable
import com.myrran.domain.mobs.common.consumable.ConsumableComponent
import com.myrran.domain.mobs.common.corporeal.Movable
import com.myrran.domain.mobs.common.corporeal.Spatial
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.Second
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableComponent
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.IMPACT_SLOT
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.SPEED
import com.myrran.domain.skills.created.form.CollisionType
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.eventbus.EventDispatcher
import ktx.math.minus

class SpellBolt(

    override val id: MobId,
    override val steerable: SteerableComponent,
    private val eventDispatcher: EventDispatcher,

    private val consumable: ConsumableComponent,
    private val collisioner: CollisionerComponent,
    val skill: Skill,
    origin: PositionMeters,
    target: PositionMeters,

): Mob, Identifiable<MobId>, Steerable by steerable, Spatial, Movable, Disposable,
    Spell, Consumable by consumable, Collisioner by collisioner
{
    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        steerable.position = origin.toBox2dUnits()
        steerable.saveLastPosition()

        val direction = target.toBox2dUnits().minus(position).nor()
        val speed = skill.getStat(SPEED)!!.totalBonus()
        steerable.applyImpulse(direction, speed.value)
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    private var state = State.NOT_EXPLODED
    override fun act(deltaTime: Float) {

        if (consumable.updateDuration(deltaTime).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))

        if (collisioner.hasCollisions() && state == State.NOT_EXPLODED ) {

            consumable.willExpireIn(Second(0.0f))

            skill.getFormSkill(IMPACT_SLOT)?.also { createForm(it) }

            collisioner.removeCollisions()
            state = State.EXPLODED
        }
    }

    private enum class State {  NOT_EXPLODED, EXPLODED }

    override fun dispose() =

        steerable.dispose()

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
