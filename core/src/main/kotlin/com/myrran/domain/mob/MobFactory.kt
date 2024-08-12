package com.myrran.domain.mob

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.metrics.Meter
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.domain.mob.metrics.SizePixels
import com.myrran.domain.mob.metrics.Speed
import com.myrran.domain.mob.player.Player
import com.myrran.domain.mob.player.StateIddle
import com.myrran.domain.mob.steerable.Spatial
import com.myrran.domain.mob.steerable.SpeedLimits
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.spell.SpellBolt
import com.myrran.infraestructure.eventbus.EventDispatcher

class MobFactory(

    private val bodyFactory: BodyFactory,
    private val eventDispatcher: EventDispatcher
)
{
    fun createPlayer(): Player {

        val body = bodyFactory.createSquareBody(SizePixels(32, 32))
        val limiter = SpeedLimits(
            maxLinearSpeed = Speed(Meter(4f)))
        val location = Spatial(
            body = body,
            limiter = limiter)
        val movable = SteeringComponent(
            location = location,
            speedLimits = limiter)
        val player = Player(
            id = MobId(),
            movable = movable,
            state = StateIddle(Vector2(0f,0f)),
            eventDispatcher = eventDispatcher)

        return player
    }

    fun createSpell(skill: Skill, origin: Vector2, target: Vector2) =

        when (skill.type) {
            SkillType.BOLT -> createSpellBolt(skill, origin, target)
        }

    private fun createSpellBolt(skill: Skill, origin: Vector2, target: Vector2): SpellBolt {

        val sizeMultiplier = skill.getStat(StatId(SpellBolt.SIZE))!!.totalBonus().value
        val radius = Pixel(16) * sizeMultiplier / 100

        val body = bodyFactory.createCircleBody(radius)
        val limiter = SpeedLimits(
            maxLinearSpeed = Speed(Meter(100f)))
        val location = Spatial(
            body = body,
            limiter = limiter)
        val movable = SteeringComponent(
            location = location,
            speedLimits = limiter)
        val spell = SpellBolt(
            id = MobId(),
            skill = skill.copy(),
            origin = origin,
            target = target,
            movable = movable,
            eventDispatcher = eventDispatcher)

        return spell
    }

    fun destroyMob(mob: Mob) =

        bodyFactory.destroyBody((mob.location as Spatial).body)
}
