package com.myrran.domain.mobs.common.corporeal

import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.math.Vector2

interface Movable: Spatial, Limiter {

    fun setLinearVelocity(direction: Vector2, value: Float)
    fun getLinearVelocity(): Vector2
    fun getAngularVelocity(): Float
    fun applyImpulse(direction: Vector2, value: Float)
    fun applyForceToCenter(force: Vector2)
    fun applyTorque(angular: Float)
}
