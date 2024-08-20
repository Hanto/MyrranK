package com.myrran.domain.world

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.caster.Caster
import com.myrran.domain.entities.mob.common.MobFactory
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.events.Event
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.events.MobCreatedEvent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.events.SpellCreatedEvent
import com.myrran.domain.misc.constants.WorldBox2D
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.eventbus.EventSender

class World(

    val player: Player,
    val spellBook: SpellBook,
    val worldBox2D: WorldBox2D,
    val mobFactory: MobFactory,
    val eventDispatcher: EventDispatcher,
    worldBox2dContactListener: ContactListener,

): EventSender by eventDispatcher, EventListener, Disposable
{
    private val mobs: MutableMap<EntityId, Mob> = mutableMapOf()
    private var toBeRemoved: MutableList<EntityId> = mutableListOf()
    private var toBeAdded: MutableList<Mob> = mutableListOf()

    init {

        worldBox2D.setContactListener(worldBox2dContactListener)
        addListener(this, PlayerSpellCastedEvent::class, FormSpellCastedEvent::class,
            MobCreatedEvent::class, MobRemovedEvent::class)
    }

    // UPDATE
    //--------------------------------------------------------------------------------------------------------

    fun update(timesStep: Float) {

        // box2d simulation
        worldBox2D.step(timesStep, 6, 2)

        // mob IA
        player.act(timesStep)
        mobs.values.forEach { it.act(timesStep) }

        // new-removed mobs
        removeMobs()
        addMobs()
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
            is MobCreatedEvent -> toBeAdded.add(event.mob)
            is MobRemovedEvent -> toBeRemoved.add(event.mob.id)
            is PlayerSpellCastedEvent -> castPlayerSpell(event.caster, event.origin)
            is FormSpellCastedEvent -> createFormSpell(event.formSkill, event.origin, event.direction)
            else -> Unit
        }
    }

    // PLAYER ACTIONS:
    //--------------------------------------------------------------------------------------------------------

    private fun castPlayerSpell(caster: Caster, origin: PositionMeters) =

        mobFactory.createSpell(caster.getSelectedSkill()!!, origin, caster.pointingAt)
            .also { toBeAdded.add(it) }
            .also { sendEvent(SpellCreatedEvent(it)) }

    private fun createFormSpell(skill: FormSkill, origin: PositionMeters, direction: Vector2) =

        mobFactory.createFormSpell(skill, origin, direction)
            .also { toBeAdded.add(it) }

    private fun pickPlayerSpell(skillId: SkillId) =

        player.changeSelecctedSkillTo(spellBook.findPlayerSkill(skillId)!!)

    // MISC:
    //--------------------------------------------------------------------------------------------------------

    private fun addMobs() {

        toBeAdded.forEach { mobs[it.id] = it }
        toBeAdded.clear()
    }

    private fun removeMobs() {

        toBeRemoved.forEach { mobId -> mobs.remove(mobId)?.also { it.dispose() } }
        toBeRemoved.clear()
    }
}
