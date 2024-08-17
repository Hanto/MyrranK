package com.myrran.domain.mobs.common.proximity

import com.badlogic.gdx.ai.steer.Proximity.ProximityCallback
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2

class ProximityComponent(

    private var owner: Steerable<Vector2>

): Proximity
{
    private var steerablesNear: MutableList<Steerable<Vector2>> = mutableListOf()

    override fun setOwner(newOwner: Steerable<Vector2>) {

        owner = newOwner }

    override fun getOwner(): Steerable<Vector2> =

        owner

    override fun findNeighbors(callback: ProximityCallback<Vector2>): Int =

        steerablesNear.forEach { callback.reportNeighbor(it) }.let { steerablesNear.size }

    override fun addNeighbor(steerable: Steerable<Vector2>) {

        steerablesNear.add(steerable) }

    override fun removeNeighbor(steerable: Steerable<Vector2>) {

        steerablesNear.remove(steerable) }
}
