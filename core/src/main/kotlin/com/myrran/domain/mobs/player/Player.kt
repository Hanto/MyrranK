package com.myrran.domain.mobs.player

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
    val eventDispatcher: EventDispatcher,

    var inputs: PlayerInputs,
    private val caster: CasterComponent,
    var state: State,

): Steerable by steerable, Spatial, Movable, Mob, Caster by caster
{
    override fun act(deltaTime: Float, world: World) {

        caster.updateCastingTime(deltaTime)

        state = state.nextState(inputs, this)
    }

    fun castSpell() =

        eventDispatcher.sendEvent(PlayerSpellCastedEvent(this, getCenter()))

    private fun getCenter(): PositionMeters =
        PositionMeters(
            Meter(position.x) + Pixel(16).toMeters(),
            Meter(position.y) + Pixel(16).toMeters())
}
