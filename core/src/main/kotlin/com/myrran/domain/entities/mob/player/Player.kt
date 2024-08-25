package com.myrran.domain.entities.mob.player

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.caster.Caster
import com.myrran.domain.entities.common.caster.CasterComponent
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.common.vulnerable.VulnerableComponent
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.infraestructure.controller.player.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Player(

    override val id: EntityId,
    override val steerable: SteerableComponent,
    val eventDispatcher: EventDispatcher,

    var inputs: PlayerInputs,
    private val vulnerable: VulnerableComponent,
    private val caster: CasterComponent,
    var state: State,

): Mob, Steerable by steerable, Corporeal, Disposable,
    Vulnerable by vulnerable, Caster by caster
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

        eventDispatcher.sendEvent(PlayerSpellCastedEvent(
            caster = this,
            skill = caster.getSelectedSkill()!!,
            origin = PositionMeters(position.x, position.y),
            target = caster.pointingAt))
}

