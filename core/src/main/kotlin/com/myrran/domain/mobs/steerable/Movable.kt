package com.myrran.domain.mobs.steerable

import com.badlogic.gdx.math.Vector2

interface Movable: Spatial {

    fun setLinearVelocity(direction: Vector2, value: Float)
    fun getLinearVelocity(): Vector2
    fun getAngularVelocity(): Float
    fun applyForceToCenter(force: Vector2)
    fun applyTorque(angular: Float)
}
