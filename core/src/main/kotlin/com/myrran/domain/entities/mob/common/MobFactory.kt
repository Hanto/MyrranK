package com.myrran.domain.entities.mob.common

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.caster.CasterComponent
import com.myrran.domain.entities.common.collisioner.CollisionerComponent
import com.myrran.domain.entities.common.collisioner.ExactLocation
import com.myrran.domain.entities.common.consumable.ConsumableComponent
import com.myrran.domain.entities.common.corporeal.BodyFactory
import com.myrran.domain.entities.common.corporeal.CorporealComponent
import com.myrran.domain.entities.common.effectable.EffectableComponent
import com.myrran.domain.entities.common.movementlimiter.MovementLimiterComponent
import com.myrran.domain.entities.common.proximityaware.ProximityAwareComponent
import com.myrran.domain.entities.common.statuses.StatusesComponent
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.entities.common.vulnerable.VulnerableComponent
import com.myrran.domain.entities.mob.enemy.Enemy
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.entities.mob.player.StateActionIddle
import com.myrran.domain.entities.mob.spells.effect.EffectFactory
import com.myrran.domain.entities.mob.spells.form.Form
import com.myrran.domain.entities.mob.spells.form.FormCircle
import com.myrran.domain.entities.mob.spells.form.FormPoint
import com.myrran.domain.entities.mob.spells.form.FormSkillType
import com.myrran.domain.entities.mob.spells.form.effectapplier.EffectApplierComponent
import com.myrran.domain.entities.mob.spells.spell.SkillType
import com.myrran.domain.entities.mob.spells.spell.SpellBolt
import com.myrran.domain.entities.mob.spells.spell.formcreator.FormCreatorComponent
import com.myrran.domain.entities.statics.Wall
import com.myrran.domain.misc.constants.SpellConstants.Companion.SIZE
import com.myrran.domain.misc.constants.WorldBox2D
import com.myrran.domain.misc.metrics.Acceleration
import com.myrran.domain.misc.metrics.AngularAcceleration
import com.myrran.domain.misc.metrics.AngularVelocity
import com.myrran.domain.misc.metrics.Degree
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
    private val effectFactory: EffectFactory,
    private val eventDispatcher: EventDispatcher,
    private val playerInputs: PlayerInputs,
)
{
    // PLAYER:
    //--------------------------------------------------------------------------------------------------------

    fun createPlayer(): Player {

        val body = bodyFactory.createPlayerBody(worldBox2D, Pixel(16))
        val limiter = MovementLimiterComponent(
            maxLinearSpeed = Speed(Meter(4f)),
            maxLinearAcceleration = Acceleration(Meter(500f)))
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
            vulnerable = VulnerableComponent(actualHPs = 200, maxHps = 200),
            caster = caster)

        body.userData = player
        return player
    }

    // ENEMY:
    //--------------------------------------------------------------------------------------------------------

    fun createEnemy(): Enemy {

        val body = bodyFactory.createEnemyBody(worldBox2D, Pixel(16))
        val limiter = MovementLimiterComponent(
            maxLinearSpeed = Speed(Meter(1f)),
            maxLinearAcceleration = Acceleration(Meter(50f)),
            maxAngularSpeed = AngularVelocity(Degree(90f).toRadians()),
            maxAngularAcceleration = AngularAcceleration(Radian(100f)))
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            isFacingBasedOnSteering = true,
            corporeal = corporeal)
        val proximity = ProximityAwareComponent(
            owner = steerable)
        val statuses = StatusesComponent()
        val effectable = EffectableComponent(
            statuses = statuses,
            eventDispatcher = eventDispatcher)
        val enemy = Enemy(
            id = EntityId(),
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            vulnerable = VulnerableComponent(actualHPs = 300, maxHps = 300),
            effectable = effectable,
            proximity = proximity)

        body.userData = enemy
        return enemy
    }

    // SPELLS:
    //--------------------------------------------------------------------------------------------------------

    fun createSpell(skill: Skill, caster: Entity, origin: PositionMeters, target: PositionMeters) =

        when (skill.type) {
            SkillType.BOLT -> createSpellBolt(skill, caster, origin, target)
        }

    private fun createSpellBolt(skill: Skill, caster: Entity, origin: PositionMeters, target: PositionMeters): SpellBolt {

        val sizeMultiplier = skill.getStat(SIZE)!!.totalBonus().value / 100
        val radius = Pixel(16) * sizeMultiplier
        val body = bodyFactory.createSpellBoltBody(worldBox2D, radius)
        val limiter = MovementLimiterComponent(
            maxLinearSpeed = Speed(Meter(100f)))
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val spell = SpellBolt(
            id = EntityId(),
            caster = caster,
            skill = skill.copy(),
            origin = origin,
            target = target,
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            consumable = ConsumableComponent(),
            collisioner = CollisionerComponent(),
            formCreator = FormCreatorComponent(eventDispatcher)
        )

        body.userData = spell
        return spell
    }

    // FORMS:
    //--------------------------------------------------------------------------------------------------------

    fun createFormSpell(formSkill: FormSkill, caster: Entity, location: ExactLocation): Form =

        when (formSkill.type) {
            FormSkillType.CIRCLE -> createFormCircle(formSkill, caster, location)
            FormSkillType.POINT -> createFormPoint(formSkill, caster, location)
        }

    private fun createFormPoint(formSkill: FormSkill, caster: Entity, location: ExactLocation): Form {

        val radius = Pixel(2)
        val body = bodyFactory.createCircleForm(worldBox2D, radius)
        val limiter = MovementLimiterComponent(
            maxLinearSpeed = Speed(Meter(0f)))
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val form = FormPoint(
            id = EntityId(),
            caster = caster,
            formSkill = formSkill.copy(),
            location = location,
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            consumable = ConsumableComponent(),
            collisioner = CollisionerComponent(),
            effectApplier = EffectApplierComponent(effectFactory)
        )

        body.userData = form
        return form
    }

    private fun createFormCircle(formSkill: FormSkill, caster: Entity, location: ExactLocation): Form {

        val sizeMultiplier = formSkill.getStat(StatId("RADIUS"))!!.totalBonus().value / 100
        val radius = Pixel(32) * sizeMultiplier
        val body = bodyFactory.createCircleForm(worldBox2D, radius)
        val limiter = MovementLimiterComponent(
            maxLinearSpeed = Speed(Meter(0f)))
        val corporeal = CorporealComponent(
            body = body,
            limiter = limiter,
            destroyFunction = { worldBox2D.destroyBody(body) })
        val steerable = SteerableComponent(
            corporeal = corporeal)
        val form = FormCircle(
            id = EntityId(),
            caster = caster,
            formSkill = formSkill.copy(),
            radius = radius.toMeters(),
            location = location,
            steerable = steerable,
            eventDispatcher = eventDispatcher,
            consumable = ConsumableComponent(),
            collisioner = CollisionerComponent(),
            effectApplier = EffectApplierComponent(effectFactory)
        )

        body.userData = form
        return form
    }

    // WALL:
    //--------------------------------------------------------------------------------------------------------

    fun createWall(size: Size<*>): Wall {

        val body = bodyFactory.createWall(worldBox2D, size)

        val wall = Wall(
            steerable = SteerableComponent(
                corporeal = CorporealComponent(
                    body = body,
                    limiter = MovementLimiterComponent(),
                    destroyFunction = { worldBox2D.destroyBody(body) }
                )
            )
        )
        body.userData = wall
        return wall
    }
}
