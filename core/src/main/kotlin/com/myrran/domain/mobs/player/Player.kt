package com.myrran.domain.mobs.player

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.caster.CasterComponent
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

): Mob, Identifiable<MobId>, Steerable by steerable, Spatial, Movable, Disposable,
    Caster by caster
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float) {

        caster.updateCastingTime(deltaTime)

        state = state.nextState(inputs, this)
    }

    override fun dispose() {

        steerable.dispose()
    }

    // OTHER:
    //--------------------------------------------------------------------------------------------------------

    fun castSpell() =

        eventDispatcher.sendEvent(PlayerSpellCastedEvent(this, PositionMeters(position.x, position.y)))
}
