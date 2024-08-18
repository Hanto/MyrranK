package com.myrran.domain.mobs.common.colisionable

import com.myrran.domain.mobs.common.steerable.Steerable

class CollisionerComponent: Collisioner
{
    private val collidedWith: MutableList<Steerable> = mutableListOf()

    override fun addCollision(solid: Steerable) {

        collidedWith.add(solid) }

    override fun retrieveCollidedWith(): Collection<Steerable> =

        collidedWith

    override fun hasCollided(): Boolean =

        collidedWith.isNotEmpty()
}
