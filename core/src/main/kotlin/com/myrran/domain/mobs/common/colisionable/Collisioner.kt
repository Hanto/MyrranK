package com.myrran.domain.mobs.common.colisionable

import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Steerable

interface Collisioner
{
    fun hasCollisions(): Boolean
    fun addCollision(solid: Steerable, pointOfCollision: PositionMeters)
    fun removeCollisions()
    fun retrieveCollisions(): Collection<Collision>
}
