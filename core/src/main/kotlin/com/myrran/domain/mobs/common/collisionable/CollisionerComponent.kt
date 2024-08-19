package com.myrran.domain.mobs.common.collisionable

import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.mobs.common.corporeal.Corporeal

class CollisionerComponent: Collisioner
{
    private val collidedWith: MutableList<Collision> = mutableListOf()

    override fun addCollision(corporeal: Corporeal, pointOfCollision: PositionMeters) {

        collidedWith.add(Collision(corporeal, pointOfCollision)) }

    override fun removeCollisions() {

        collidedWith.clear() }

    override fun retrieveCollisions(): Collection<Collision> =

        collidedWith

    override fun hasCollisions(): Boolean =

        collidedWith.isNotEmpty()

    // COLLISION:
    //--------------------------------------------------------------------------------------------------------

    data class Collision(
        val corporeal: Corporeal,
        val pointOfCollision: PositionMeters
    )
}
