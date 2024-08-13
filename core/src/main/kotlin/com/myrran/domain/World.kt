package com.myrran.domain

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.Event
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.events.SpellCreatedEvent
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobFactory
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import com.myrran.domain.skills.SpellBook
import com.myrran.infraestructure.controller.player.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.eventbus.EventSender

class World(

    val player: Player,
    val spellBook: SpellBook,
    val worldBox2D: WorldBox2D,
    val mobFactory: MobFactory,
    val eventDispatcher: EventDispatcher

): EventSender by eventDispatcher, EventListener, Disposable
{
    private val mobs: MutableMap<MobId, Mob> = mutableMapOf()
    private var toBeRemoved: MutableList<MobId> = mutableListOf()

    init {

        addListener(listener = this, PlayerSpellCastedEvent::class, MobRemovedEvent::class)
    }

    // UPDATE
    //--------------------------------------------------------------------------------------------------------

    fun update(timesStep: Float) {

        // box2d simulation
        worldBox2D.step(timesStep, 8, 3)

        // mob IA
        player.act(timesStep, this)
        mobs.values.forEach { it.act(timesStep, this) }

        // death mobs
        toBeRemoved.forEach { removeMob(it) }
        toBeRemoved.clear()
    }

    fun saveLastPosition() {

        player.saveLastPosition()
        mobs.values.forEach { it.saveLastPosition() }
    }

    // EVENTS:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() {

        worldBox2D.dispose()
        removeListener(listener = this)
    }

    override fun handleEvent(event: Event) {

        when (event) {
            is PlayerSpellCastedEvent -> castPlayerSpell(event.caster, event.origin)
            is MobRemovedEvent -> toBeRemoved.add(event.mob.id)
            else -> Unit
        }
    }

    // MISC:
    //--------------------------------------------------------------------------------------------------------

    fun applyPlayerInputs(inputs: PlayerInputs) =

        player.applyInputs(inputs)

    private fun castPlayerSpell(caster: Caster, origin: PositionMeters) {

        val skill = spellBook.findPlayerSkill(caster.selectedSkillId!!)!!
        val spell = mobFactory.createSpell(skill, origin, caster.pointingAt)
        caster.setCastingTime(skill.getCastingTime())

        addMob(spell)
        sendEvent(SpellCreatedEvent(spell))
    }

    private fun addMob(mob: Mob) {

        mobs[mob.id] = mob }

    private fun removeMob(id: MobId) {

        mobs.remove(id)
            ?.also { mobFactory.destroyMob(it) }
    }
}
