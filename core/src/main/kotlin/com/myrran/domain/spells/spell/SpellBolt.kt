package com.myrran.domain.spells.spell

import com.badlogic.gdx.math.Vector2
import com.myrran.application.World
import com.myrran.domain.events.WorldEvent.MobRemovedEvent
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.Movable
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.infraestructure.eventbus.EventDispatcher
import ktx.math.minus

class SpellBolt(

    override val id: MobId,
    val skill: Skill,
    private val movable: SteeringComponent,
    val eventDispatcher: EventDispatcher,
    origin: Vector2,
    target: Vector2,

): Movable by movable, Mob, Spell
{
    companion object {

        const val SPEED = "SPEED"
        const val SIZE = "SIZE"
    }

    var timeToLife = 2f
    override var toBeRemoved = false

    init {

        position = origin
        val direction = target.minus(position).nor()
        val speed = skill.getStat(StatId(SPEED))!!.totalBonus()

        setLinearVelocity(direction, speed.value)
    }

    override fun act(deltaTime: Float, world: World) {

        timeToLife -= deltaTime

        if(timeToLife <0) {

            eventDispatcher.sendEvent(MobRemovedEvent(id))
        }
    }
}
