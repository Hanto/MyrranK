package com.myrran.domain.mobs.mob

import com.badlogic.gdx.ai.steer.Proximity
import com.badlogic.gdx.ai.steer.Proximity.ProximityCallback
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.World
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Enemy(

    override val id: MobId,
    override val steerable: SteerableByBox2DComponent,
    val eventDispatcher: EventDispatcher


): Mob, Steerable by steerable, Spatial, Movable, Proximity<Vector2>
{
    var enemiesNear: MutableList<Mob> = mutableListOf()

    override fun act(deltaTime: Float, world: World)
    {
        steerable.update(deltaTime)
    }

    override fun getOwner(): Steerable = this
    override fun setOwner(owner: com.badlogic.gdx.ai.steer.Steerable<Vector2>) = Unit
    override fun findNeighbors(callback: ProximityCallback<Vector2>): Int =

        enemiesNear.forEach { callback.reportNeighbor(it) }.let { enemiesNear.size }
}
