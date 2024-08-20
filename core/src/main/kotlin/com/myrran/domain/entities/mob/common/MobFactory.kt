package com.myrran.domain.entities.mob.common

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.caster.CasterComponent
import com.myrran.domain.entities.common.collisionable.CollisionerComponent
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.corporeal.BodyFactory
import com.myrran.domain.entities.common.corporeal.CorporealComponent
import com.myrran.domain.entities.common.corporeal.MovementLimiter
import com.myrran.domain.entities.common.proximityaware.ProximityAwareComponent
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.entities.common.vulnerable.VulnerableComponent
import com.myrran.domain.entities.mob.enemy.Enemy
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.entities.mob.player.StateActionIddle
import com.myrran.domain.entities.mob.spells.form.Form
import com.myrran.domain.entities.mob.spells.form.FormCircle
import com.myrran.domain.entities.mob.spells.form.FormPoint
import com.myrran.domain.entities.mob.spells.form.FormSkillType
import com.myrran.domain.entities.mob.spells.spell.SkillType
import com.myrran.domain.entities.mob.spells.spell.SpellBolt
import com.myrran.domain.entities.statics.Wall
import com.myrran.domain.misc.constants.SpellConstants.Companion.SIZE
import com.myrran.domain.misc.constants.WorldBox2D
import com.myrran.domain.misc.metrics.Acceleration
import com.myrran.domain.misc.metrics.AngularAcceleration
import com.myrran.domain.misc.metrics.AngularVelocity
import com.myrran.domain.misc.metrics.Meter
import com.myrran.domain.misc.metrics.Pixel
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.misc.metrics.Radian
import com.myrran.domain.misc.metrics.Size
import com.myrran.domain.misc.metrics.Speed
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.stat.StatId
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

        val body = bodyFactory.createPlayerBody(worldBox2D, Pixel(16))
        val limiter = MovementLimiter(
            maxLinearSpeed = Speed(Meter(4f)),
            maxLinearAcceleration = Acceleration(Meter(500f))
        )
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val caster = CasterComponent()
        val player = Player(
            id = EntityId(),
            steerable = steerable,
            state = StateActionIddle,
            eventDispatcher = eventDispatcher,
            inputs = playerInputs,
            vulnerable = VulnerableComponent(200, 200),
            caster = caster)

        body.userData = player
        return player
    }

    fun createEnemy(): Enemy {

        val body = bodyFactory.createEnemyBody(worldBox2D, Pixel(16))
        val limiter = MovementLimiter(
            maxLinearSpeed = Speed(Meter(2f)),
            maxLinearAcceleration = Acceleration(Meter(500f)),
            maxAngularSpeed = AngularVelocity(Radian(6f)),
            maxAngularAcceleration = AngularAcceleration(Radian(12f))
        )
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val proximity = ProximityAwareComponent(
            owner = steerable)
        val enemy = Enemy(
            id = EntityId(),
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            proximity = proximity)

        body.userData = enemy
        return enemy
    }

    fun createSpell(skill: Skill, origin: PositionMeters, target: PositionMeters) =

        when (skill.type) {
            SkillType.BOLT -> createSpellBolt(skill, origin, target)
        }

    private fun createSpellBolt(skill: Skill, origin: PositionMeters, target: PositionMeters): SpellBolt {

        val sizeMultiplier = skill.getStat(SIZE)!!.totalBonus().value / 100
        val radius = Pixel(16) * sizeMultiplier
        val body = bodyFactory.createSpellBoltBody(worldBox2D, radius)
        val limiter = MovementLimiter(
            maxLinearSpeed = Speed(Meter(100f))
        )
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val spell = SpellBolt(
            id = EntityId(),
            skill = skill.copy(),
            origin = origin,
            target = target,
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            consumable = ConsumableComponent(),
            collisioner = CollisionerComponent()
        )

        body.userData = spell
        return spell
    }

    fun createFormSpell(formSkill: FormSkill, origin: PositionMeters, direction: Vector2): Form =

        when (formSkill.type) {
            FormSkillType.CIRCLE -> createFormCircle(formSkill, origin, direction)
            FormSkillType.POINT -> createFormPoint(formSkill, origin, direction)
        }

    private fun createFormPoint(formSkill: FormSkill, origin: PositionMeters, direction: Vector2): Form {

        val radius = Pixel(2)
        val body = bodyFactory.createCircleForm(worldBox2D, radius)
        val limiter = MovementLimiter(
            maxLinearSpeed = Speed(Meter(0f))
        )
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val form = FormPoint(
            id = EntityId(),
            formSkill = formSkill.copy(),
            origin = origin,
            direction = direction,
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            consumable = ConsumableComponent(),
            collisioner = CollisionerComponent()
        )

        body.userData = form
        return form
    }

    private fun createFormCircle(formSkill: FormSkill, origin: PositionMeters, direction: Vector2): Form {

        val sizeMultiplier = formSkill.getStat(StatId("RADIUS"))!!.totalBonus().value / 100
        val radius = Pixel(64) * sizeMultiplier
        val body = bodyFactory.createCircleForm(worldBox2D, radius)
        val limiter = MovementLimiter(
            maxLinearSpeed = Speed(Meter(0f))
        )
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val form = FormCircle(
            id = EntityId(),
            formSkill = formSkill.copy(),
            origin = origin,
            direction = direction,
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            consumable = ConsumableComponent(),
            collisioner = CollisionerComponent()
        )

        body.userData = form
        return form
    }

    fun createWall(size: Size<*>): Wall {

        val body = bodyFactory.createWall(worldBox2D, size)

        val wall = Wall(
            steerable = SteerableComponent(
                corporeal = CorporealComponent(
                    body = body,
                    limiter = MovementLimiter(),
                    destroyFunction = { worldBox2D.destroyBody(body) }
                )
            )
        )
        body.userData = wall
        return wall
    }
}
