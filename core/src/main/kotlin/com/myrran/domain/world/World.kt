package com.myrran.domain.world

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.WorldEvent
import com.myrran.domain.misc.observer.JavaObservable
import com.myrran.domain.misc.observer.Observable
import com.myrran.domain.mob.BodyFactory
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.Player
import com.myrran.domain.mob.PlayerFactory
import com.myrran.infraestructure.input.PlayerInputs
import com.badlogic.gdx.physics.box2d.World as Box2DWorld

class World(

    private val playerInputs: PlayerInputs,
    val box2dWorld: Box2DWorld = Box2DWorld(Vector2(0f, 0f) ,true),
    private val playerFactory: PlayerFactory = PlayerFactory(BodyFactory(box2dWorld), playerInputs),
    val player: Player = playerFactory.createPlayer(),
    private val mobs: MutableMap<MobId, Mob> = mutableMapOf(),
    private val observable: Observable<WorldEvent> = JavaObservable()

): Observable<WorldEvent> by observable, Disposable
{
    fun addMob(mob: Mob) {

        mobs[mob.id] = mob }

    fun update(timesStep: Float) {

        player.update(timesStep, this)
        box2dWorld.step(timesStep, 8, 3)
    }

    fun saveLastPosition() {

        player.saveLastPosition()
    }

    override fun dispose() {

        box2dWorld.dispose()
    }
}
