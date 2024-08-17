package com.myrran.domain

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.Event
import com.myrran.domain.events.MobCreatedEvent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.events.SpellCreatedEvent
import com.myrran.domain.mobs.common.ColissionListener
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobFactory
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import com.myrran.domain.skills.SpellBook
import com.myrran.domain.skills.created.skill.SkillId
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

        addListener(this, PlayerSpellCastedEvent::class, MobRemovedEvent::class, MobCreatedEvent::class)
        worldBox2D.setContactListener(ColissionListener())
    }

    // UPDATE
    //--------------------------------------------------------------------------------------------------------

    fun update(timesStep: Float) {

        // box2d simulation
        worldBox2D.step(timesStep, 6, 2)

        // mob IA
        player.act(timesStep)
        mobs.values.forEach { it.act(timesStep) }

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
        removeListener( this)
    }

    override fun handleEvent(event: Event) {

        when (event) {
            is MobCreatedEvent -> addMob(event.mob)
            is MobRemovedEvent -> toBeRemoved.add(event.mob.id)
            is PlayerSpellCastedEvent -> castPlayerSpell(event.caster, event.origin)
            else -> Unit
        }
    }

    // PLAYER ACTIONS:
    //--------------------------------------------------------------------------------------------------------

    private fun castPlayerSpell(caster: Caster, origin: PositionMeters) =

        mobFactory.createSpell(caster.getSelectedSkill()!!, origin, caster.pointingAt)
            .also { addMob(it) }
            .also { sendEvent(SpellCreatedEvent(it)) }

    private fun pickPlayerSpell(skillId: SkillId) =

        player.changeSelecctedSkillTo(spellBook.findPlayerSkill(skillId)!!)

    // MISC:
    //--------------------------------------------------------------------------------------------------------

    private fun addMob(mob: Mob) {

        mobs[mob.id] = mob }

    private fun removeMob(id: MobId) {

        mobs.remove(id)
            ?.also { it.dispose() }
    }
}
