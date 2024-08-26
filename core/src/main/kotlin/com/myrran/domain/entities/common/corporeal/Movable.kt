package com.myrran.domain.entities.common.corporeal

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.movementlimiter.MovementLimiter

interface Movable: Spatial, MovementLimiter {

    fun setLinearVelocity(direction: Vector2, value: Float)
    fun getLinearVelocity(): Vector2
    fun getAngularVelocity(): Float
    fun applyImpulse(direction: Vector2, value: Float)
    fun applyForceToCenter(force: Vector2)
    fun applyTorque(angular: Float)
}
