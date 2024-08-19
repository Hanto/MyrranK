package com.myrran.domain.mobs.common.colisionable

import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Steerable

class CollisionerComponent: Collisioner
{
    private val collidedWith: MutableList<Collision> = mutableListOf()

    override fun addCollision(solid: Steerable, pointOfCollision: PositionMeters) {

        collidedWith.add(Collision(solid, pointOfCollision)) }

    override fun removeCollisions() {

        collidedWith.clear() }

    override fun retrieveCollisions(): Collection<Collision> =

        collidedWith

    override fun hasCollisions(): Boolean =

        collidedWith.isNotEmpty()

    // COLLISION:
    //--------------------------------------------------------------------------------------------------------

    data class Collision(
        val steerable: Steerable,
        val pointOfCollision: PositionMeters
    )
}

