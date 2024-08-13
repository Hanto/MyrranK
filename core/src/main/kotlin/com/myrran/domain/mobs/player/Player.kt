package com.myrran.domain.mobs.player

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.World
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.caster.CasterComponent
import com.myrran.domain.mobs.common.metrics.Meter
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.infraestructure.controller.player.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Player(

    override val id: MobId,
    override val steerable: SteerableByBox2DComponent,
    private val eventDispatcher: EventDispatcher,

    private val caster: CasterComponent,
    var state: State = StateIddle(Vector2(0f, 0f)),

): Steerable by steerable, Spatial, Movable, Mob, Caster by caster
{
    private var tryToCast = false

    override fun act(deltaTime: Float, world: World) {

        caster.updateCastingTime(deltaTime)

        if (tryToCast && caster.isReadyToCast()) {

            eventDispatcher.sendEvent(PlayerSpellCastedEvent(
                caster = caster,
                origin = getCenter()) )
        }
    }

    fun applyInputs(inputs: PlayerInputs) {

        state = state.nextState(inputs)
        steerable.setLinearVelocity(state.direction, maxLinearSpeed)
        tryToCast = inputs.tryToCast
    }

    private fun getCenter(): PositionMeters =
        PositionMeters(
            Meter(position.x) + Pixel(16).toMeters(),
            Meter(position.y) + Pixel(16).toMeters())
}
