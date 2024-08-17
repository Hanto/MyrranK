package com.myrran.domain.mobs.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.World
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.consumable.Consumable
import com.myrran.domain.mobs.common.consumable.ConsumableComponent
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.SPEED
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.eventbus.EventDispatcher
import ktx.math.minus

class SpellBolt(

    override val id: MobId,
    override val steerable: SteerableByBox2DComponent,
    private val eventDispatcher: EventDispatcher,

    private val consumable: ConsumableComponent,
    val skill: Skill,
    origin: PositionMeters,
    target: PositionMeters,

): Mob, Spell, Steerable by steerable, Spatial, Movable, Consumable by consumable, Disposable
{
    init {

        steerable.position = origin.toBox2dUnits()
        steerable.saveLastPosition()

        val direction = target.toBox2dUnits().minus(position).nor()
        val speed = skill.getStat(SPEED)!!.totalBonus()
        steerable.setLinearVelocity(direction, speed.value)
    }

    override fun act(deltaTime: Float, world: World) {

        if (consumable.updateDuration(deltaTime).isConsumed)
            eventDispatcher.sendEvent(MobRemovedEvent(this))
    }

    override fun dispose() =

        steerable.dispose()
}
