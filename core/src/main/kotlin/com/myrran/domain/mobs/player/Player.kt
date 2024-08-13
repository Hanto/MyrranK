package com.myrran.domain.mobs.player

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.World
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.mobs.Mob
import com.myrran.domain.mobs.MobId
import com.myrran.domain.mobs.common.metrics.Meter
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.Position
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.SteerableAI
import com.myrran.domain.mobs.common.steerable.SteerableByBox2D
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.infraestructure.controller.player.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Player(

    override val id: MobId,
    override val steerable: SteerableByBox2D,
    private val eventDispatcher: EventDispatcher,

    var state: State = StateIddle(Vector2(0f, 0f)),

): SteerableAI by steerable, Spatial, Movable, Mob
{
    var pointingAt: Position<Meter> = PositionMeters(0f, 0f)
    private var doCast = false

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

    fun applyInputs(inputs: PlayerInputs) {

        state = state.nextState(inputs)
        steerable.setLinearVelocity(state.direction, maxLinearSpeed)
        doCast = inputs.doCast
    }
}
