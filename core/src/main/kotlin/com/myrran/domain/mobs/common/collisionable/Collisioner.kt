package com.myrran.domain.mobs.common.collisionable

import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.mobs.common.collisionable.CollisionerComponent.Collision
import com.myrran.domain.mobs.common.corporeal.Corporeal

interface Collisioner
{
    fun hasCollisions(): Boolean
    fun hasCollidedAWall(): Boolean
    fun addCollision(corporeal: Corporeal, pointOfCollision: PositionMeters)
    fun removeCollisions()
    fun retrieveCollisions(): Collection<Collision>
}
