package com.myrran.domain.entities.common.collisioner

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.statics.Wall
import com.myrran.domain.misc.metrics.PositionMeters

class CollisionerComponent: Collisioner
{
    private val collidedWith: MutableList<Collision> = mutableListOf()

    override fun addCollision(corporeal: Corporeal, pointOfCollision: PositionMeters, direction: Vector2) {

        val location = ExactLocation(pointOfCollision, direction)
        collidedWith.add(Collision(corporeal, location)) }

    override fun removeCollisions() {

        collidedWith.clear() }

    override fun retrieveCollisions(): Collection<Collision> =

        collidedWith

    override fun hasCollisions(): Boolean =

        collidedWith.isNotEmpty()

    override fun hasCollidedAWall(): Boolean =

        collidedWith.any { it.corporeal is Wall }
}
