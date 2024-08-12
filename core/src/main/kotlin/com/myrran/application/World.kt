package com.myrran.application

import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.WorldEvent
import com.myrran.domain.misc.observer.JavaObservable
import com.myrran.domain.misc.observer.Observable
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobFactory
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.player.Player
import com.myrran.infraestructure.controller.PlayerInputs
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventDispatcher.MsgCode.PlayerSpellCastedMsg
import com.myrran.infraestructure.eventbus.EventDispatcher.MsgCode.RemoveMobEvent
import com.badlogic.gdx.physics.box2d.World as Box2DWorld

class World(

    val player: Player,
    val spellBook: SpellBook,
    val box2dWorld: Box2DWorld,
    val mobFactory: MobFactory,
    val eventDispatcher: EventDispatcher,
    private val observable: Observable<WorldEvent> = JavaObservable()

): Observable<WorldEvent> by observable, Disposable, Telegraph
{
    private val mobs: MutableMap<MobId, Mob> = mutableMapOf()
    private var toBeRemoved: MutableList<MobId> = mutableListOf()

    init {

        eventDispatcher.addListener(this, PlayerSpellCastedMsg, RemoveMobEvent)
    }

    fun applyPlayerInputs(inputs: PlayerInputs) =

        player.applyInputs(inputs)

    // UPDATE
    //--------------------------------------------------------------------------------------------------------

    fun update(timesStep: Float) {

        // box2d simulation
        box2dWorld.step(timesStep, 8, 3)

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

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() {

        box2dWorld.dispose()
        eventDispatcher.removeListener(this)
    }

    override fun handleMessage(msg: Telegram): Boolean =

        when (msg.message) {
            PlayerSpellCastedMsg.value -> castPlayerSpell(msg.extraInfo as WorldEvent.PlayerSpellCastedEvent).let { true }
            RemoveMobEvent.value -> toBeRemoved.add((msg.extraInfo as WorldEvent.RemoveMobEvent).mobId).let { true }
            else -> true
        }

    private fun castPlayerSpell(event: WorldEvent.PlayerSpellCastedEvent) {

        val skill = spellBook.created.findBy(event.skillId)!!
        val spell = mobFactory.createSpell(skill, event.origin.toBox2dUnits(), event.target.toBox2dUnits())

        addMob(spell)
    }

    private fun addMob(mob: Mob) {

        mobs[mob.id] = mob }

    private fun removeMob(id: MobId) {

        mobs.remove(id)
            ?.also { mobFactory.destroyMob(it) }
    }
}
