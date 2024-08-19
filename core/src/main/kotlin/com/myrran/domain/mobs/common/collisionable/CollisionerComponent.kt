package com.myrran.domain.mobs.common.collisionable

import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.metrics.PositionMeters

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
