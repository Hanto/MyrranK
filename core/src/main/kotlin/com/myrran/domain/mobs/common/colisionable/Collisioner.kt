package com.myrran.domain.mobs.common.colisionable

import com.myrran.domain.mobs.common.steerable.Steerable

interface Collisioner
{
    fun hasCollided(): Boolean
    fun addCollision(solid: Steerable)
    fun retrieveCollidedWith(): Collection<Steerable>
}
