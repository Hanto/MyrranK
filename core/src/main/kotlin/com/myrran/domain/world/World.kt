package com.myrran.domain.world

import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.collisioner.ExactLocation
import com.myrran.domain.entities.mob.common.MobFactory
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.events.Event
import com.myrran.domain.events.FormCreatedEvent
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.events.MobCreatedEvent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.events.PlayerSpellCastedEvent
import com.myrran.domain.events.SpellCreatedEvent
import com.myrran.domain.misc.constants.WorldBox2D
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.world.damagesystem.DamageSystem
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.eventbus.EventSender

class World(

    val player: Player,
    val spellBook: SpellBook,
    val worldBox2D: WorldBox2D,
    val mobFactory: MobFactory,
    val damageSystem: DamageSystem,
    val eventDispatcher: EventDispatcher,
    worldBox2dContactListener: ContactListener,

): EventSender by eventDispatcher, EventListener, Disposable
{
    private val mobs: MutableMap<EntityId, Mob> = mutableMapOf()
    private var toBeRemoved: MutableList<EntityId> = mutableListOf()
    private var toBeAdded: MutableList<Mob> = mutableListOf()

    init {

        worldBox2D.setContactListener(worldBox2dContactListener)
        mobs[player.id] = player

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

        // damageSystem
        mobs.values.forEach { damageSystem.applyDamages(it) }

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
            is PlayerSpellCastedEvent -> castPlayerSpell(event.skill, event.caster, event.origin, event.target)
            is FormSpellCastedEvent -> createFormSpell(event.formSkill, event.caster, event.location)
            else -> Unit
        }
    }

    // PLAYER ACTIONS:
    //--------------------------------------------------------------------------------------------------------

    private fun castPlayerSpell(skill: Skill, caster: Entity, origin: PositionMeters, target: PositionMeters) =

        mobFactory.createSpell(skill, caster, origin, target)
            .also { toBeAdded.add(it) }
            .also { sendEvent(SpellCreatedEvent(it)) }

    private fun createFormSpell(skill: FormSkill, caster: Entity, location: ExactLocation) =

        mobFactory.createFormSpell(skill, caster, location)

            .also { toBeAdded.add(it) }
            .also { sendEvent(FormCreatedEvent(it)) }

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
