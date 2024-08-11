package com.myrran.application

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.WorldEvent
import com.myrran.domain.misc.observer.JavaObservable
import com.myrran.domain.misc.observer.Observable
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobFactory
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.player.Player
import com.myrran.infraestructure.controller.PlayerInputs
import com.badlogic.gdx.physics.box2d.World as Box2DWorld

class World(

    val player: Player,
    val box2dWorld: Box2DWorld,
    private val mobFactory: MobFactory,
    private val observable: Observable<WorldEvent> = JavaObservable()

): Observable<WorldEvent> by observable, Disposable
{
    private val mobs: MutableMap<MobId, Mob> = mutableMapOf()

    fun addMob(mob: Mob) {

        mobs[mob.id] = mob }

    fun applyPlayerInputs(inputs: PlayerInputs) =

        player.applyInputs(inputs)

    fun update(timesStep: Float) {

        box2dWorld.step(timesStep, 8, 3)
    }

    fun saveLastPosition() {

        player.saveLastPosition()
        mobs.values.forEach { it.saveLastPosition() }
    }

    override fun dispose() {

        box2dWorld.dispose()
    }
}
