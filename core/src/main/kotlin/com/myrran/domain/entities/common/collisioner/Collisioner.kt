package com.myrran.domain.entities.common.collisioner

import com.myrran.domain.entities.common.collisioner.CollisionerComponent.Collision
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.misc.metrics.PositionMeters

interface Collisioner
{
    fun hasCollisions(): Boolean
    fun hasCollidedAWall(): Boolean
    fun addCollision(corporeal: Corporeal, pointOfCollision: PositionMeters)
    fun removeCollisions()
    fun retrieveCollisions(): Collection<Collision>
}
