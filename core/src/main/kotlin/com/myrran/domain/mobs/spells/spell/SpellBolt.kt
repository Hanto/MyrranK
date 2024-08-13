package com.myrran.domain.mobs.spells.spell

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.World
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.mobs.Mob
import com.myrran.domain.mobs.MobId
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.RANGE
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.SPEED
import com.myrran.domain.mobs.steerable.Movable
import com.myrran.domain.mobs.steerable.Spatial
import com.myrran.domain.mobs.steerable.SteerableAI
import com.myrran.domain.mobs.steerable.SteerableByBox2D
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.eventbus.EventDispatcher
import ktx.math.minus

class SpellBolt(

    override val id: MobId,
    override val steerable: SteerableByBox2D,
    private val eventDispatcher: EventDispatcher,

    val skill: Skill,
    origin: Vector2,
    target: Vector2,

): SteerableAI by steerable, Spatial, Movable, Mob, Spell
{
    private var timeToLife = skill.getStat(RANGE)!!.totalBonus().value

    init {

        position = origin
        saveLastPosition()

        val direction = target.minus(position).nor()
        val speed = skill.getStat(SPEED)!!.totalBonus()
        setLinearVelocity(direction, speed.value)
    }

    override fun act(deltaTime: Float, world: World) {

        timeToLife -= deltaTime

        if(timeToLife <0) {

            eventDispatcher.sendEvent(MobRemovedEvent(this))
        }
    }
}
