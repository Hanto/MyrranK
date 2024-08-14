package com.myrran.domain.mobs.common

import com.myrran.domain.mobs.common.caster.CasterComponent
import com.myrran.domain.mobs.common.consumable.ConsumableComponent
import com.myrran.domain.mobs.common.metrics.Meter
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.Second
import com.myrran.domain.mobs.common.metrics.SizePixels
import com.myrran.domain.mobs.common.metrics.Speed
import com.myrran.domain.mobs.common.steerable.BodyFactory
import com.myrran.domain.mobs.common.steerable.MovableByBox2D
import com.myrran.domain.mobs.common.steerable.SpeedLimiter
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.player.StateTacticalIddle
import com.myrran.domain.mobs.spells.spell.SkillType
import com.myrran.domain.mobs.spells.spell.SpellBolt
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.RANGE
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.SIZE
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.infraestructure.controller.player.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher

class MobFactory(

    private val worldBox2D: WorldBox2D,
    private val bodyFactory: BodyFactory,
    private val eventDispatcher: EventDispatcher,
    private val playerInputs: PlayerInputs,
)
{
    fun createPlayer(): Player {

        val body = bodyFactory.createSquareBody(worldBox2D, SizePixels(32, 32))
        val limiter = SpeedLimiter(
            maxLinearSpeed = Speed(Meter(4f)))
        val location = MovableByBox2D(
            body = body,
            limiter = limiter)
        val movable = SteerableByBox2DComponent(
            movable = location,
            speedLimiter = limiter)
        val caster = CasterComponent()
        val player = Player(
            id = MobId(),
            steerable = movable,
            state = StateTacticalIddle,
            eventDispatcher = eventDispatcher,
            inputs = playerInputs,
            caster = caster)

        return player
    }

    fun createSpell(skill: Skill, origin: PositionMeters, target: PositionMeters) =

        when (skill.type) {
            SkillType.BOLT -> createSpellBolt(skill, origin, target)
        }

    private fun createSpellBolt(skill: Skill, origin: PositionMeters, target: PositionMeters): SpellBolt {

        val sizeMultiplier = skill.getStat(SIZE)!!.totalBonus().value
        val radius = Pixel(16) * sizeMultiplier / 100
        val duration = Second(skill.getStat(RANGE)!!.totalBonus().value)
        val consumable = ConsumableComponent(duration)

        val body = bodyFactory.createCircleBody(worldBox2D, radius)
        val limiter = SpeedLimiter(
            maxLinearSpeed = Speed(Meter(100f)))
        val location = MovableByBox2D(
            body = body,
            limiter = limiter)
        val movable = SteerableByBox2DComponent(
            movable = location,
            speedLimiter = limiter)
        val spell = SpellBolt(
            id = MobId(),
            skill = skill.copy(),
            origin = origin,
            target = target,
            steerable = movable,
            eventDispatcher = eventDispatcher,
            consumable = consumable)

        return spell
    }

    fun destroyMob(mob: Mob) {

        if (mob.steerable is SteerableByBox2DComponent)
            (mob.steerable as SteerableByBox2DComponent).destroyBody(worldBox2D)
    }
}
