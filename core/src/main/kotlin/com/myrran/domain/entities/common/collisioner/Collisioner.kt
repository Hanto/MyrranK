package com.myrran.domain.entities.common.collisioner

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.misc.metrics.PositionMeters

interface Collisioner
{
    fun hasCollisions(): Boolean
    fun hasCollidedAWall(): Boolean
    fun addCollision(corporeal: Corporeal, pointOfCollision: PositionMeters, direction: Vector2)
    fun removeCollisions()
    fun retrieveCollisions(): Collection<Collision>
}
