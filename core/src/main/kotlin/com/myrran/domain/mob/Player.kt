package com.myrran.domain.mob

import com.badlogic.gdx.math.Vector2
import com.myrran.application.World
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.mob.metrics.Meter
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.domain.mob.metrics.Position
import com.myrran.domain.mob.metrics.PositionMeters
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.infraestructure.controller.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Player(

    override val id: MobId,
    private val movable: SteeringComponent,
    var state: State = StateIddle(Vector2(0f, 0f)),
    val eventDispatcher: EventDispatcher,

): Movable by movable, Mob
{
    var pointingAt: Position<Meter> = PositionMeters(0f, 0f)
    private var doCast = false

    fun applyInputs(inputs: PlayerInputs) {

        state = state.nextState(inputs)
        val force = state.direction.cpy().scl(1000f)
        movable.setLinearVelocity(state.direction, maxLinearSpeed)
        //movable.spatial.applyForceToCenter(force)

        doCast = inputs.doCast
        //pointingAt = inputs.touchedWorld
    }

    override fun act(deltaTime: Float, world: World) {

        if (doCast) {

            val characterCenter = PositionMeters(
                Meter(position.x) + Pixel(16).toMeters(),
                Meter(position.y) + Pixel(16).toMeters())

            eventDispatcher.sendEvent(PlayerSpellCastedEvent(
                mobId = id,
                skillId = SkillId.from("5e2d588d-cc7e-4475-87da-622409e4eb31"),
                origin = characterCenter,
                target = pointingAt) )

            doCast = false
        }
    }
}
