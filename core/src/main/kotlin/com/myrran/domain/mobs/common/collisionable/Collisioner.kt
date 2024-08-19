package com.myrran.domain.mobs.common.collisionable

import com.myrran.domain.mobs.common.collisionable.CollisionerComponent.Collision
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.metrics.PositionMeters

interface Collisioner
{
    fun hasCollisions(): Boolean
    fun addCollision(corporeal: Corporeal, pointOfCollision: PositionMeters)
    fun removeCollisions()
    fun retrieveCollisions(): Collection<Collision>
}
